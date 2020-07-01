package com.app.base.ui.dashboard.ui.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.app.base.R
import com.app.base.api.service.ApiHelper
import com.app.base.api.service.ExampleEphemeralKeyProvider
import com.app.base.base.BaseActivity
import com.app.base.data.payment.PaymentIntentResponse
import com.google.gson.GsonBuilder
import com.stripe.android.*
import com.stripe.android.model.*
import com.stripe.android.view.PaymentMethodsActivityStarter
import kotlinx.android.synthetic.main.activity_payment.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//https://threadedcoder.com/how-to-integrate-stripe-payments-on-android/
class PaymentActivity : BaseActivity() {
    private lateinit var stripe: Stripe
    private val notSelectedText: String by lazy { getString(R.string.not_selected) }

    private lateinit var clientSecret: String
    private lateinit var paymentMethodId: String
    private var paymentSessionData: PaymentSessionData? = null

    private val paymentSession: PaymentSession by lazy {
        PaymentSession(
                activity = this,
                config = PaymentSessionConfig.Builder()
                        .setShippingMethodsRequired(false)
                        .build()
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        attachClickListeners()
        CustomerSession.initCustomerSession(this, ExampleEphemeralKeyProvider(getLoginInfo()!!.customerId), true)
        getClientSecret()
        initPaymentSession(createCustomerSession())
    }

    private fun getClientSecret() {
        val webService = ApiHelper.createService()
        webService.createPaymentIntent(getLoginInfo()!!.customerId!!).enqueue(object : Callback<PaymentIntentResponse> {
            override fun onResponse(call: Call<PaymentIntentResponse>?, response: Response<PaymentIntentResponse>?) {
                response?.body()?.let {
                    clientSecret = it.clientSecret!!
                }
            }

            override fun onFailure(call: Call<PaymentIntentResponse>?, t: Throwable?) {
                t?.let {
                    showToastMsg(it.localizedMessage)
                }
            }
        })
    }


    private fun attachClickListeners() {
        ivToolbarBack.setOnClickListener {
            onBackPressed()
        }

        btnPaymentMethod.setOnClickListener {
            PaymentMethodsActivityStarter(this)
                    .startForResult(PaymentMethodsActivityStarter.Args.Builder().build()
                    )
        }

        btnPayNow.setOnClickListener {
            makePayment()
        }
    }

    private fun makePayment() {
        if (this::paymentMethodId.isInitialized) {
            showLoading(true)
            stripe = Stripe(applicationContext, PaymentConfiguration.getInstance(applicationContext).publishableKey)
            stripe.confirmPayment(this,
                    ConfirmPaymentIntentParams.createWithPaymentMethodId(paymentMethodId,
                            clientSecret, "stripe://payment_auth"))
        } else {
            showToastMsg("Choose payment method")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PaymentMethodsActivityStarter.REQUEST_CODE &&
                resultCode == Activity.RESULT_OK && data != null
        ) {
            val paymentMethod = PaymentMethodsActivityStarter.Result.fromIntent(data)?.paymentMethod
            paymentMethod!!.card?.let { card ->
                btnPaymentMethod.text = buildCardString(card)
            }
            paymentMethodId = paymentMethod.id!!
        } else {
            // Handle the result of stripe.confirmPayment
            if (this::stripe.isInitialized)
                stripe.onPaymentResult(requestCode, data, PaymentResultCallback(this));
            else
               showLoading(false)
        }

    }

    private fun buildCardString(data: PaymentMethod.Card): String {
        return getString(R.string.ending_in, data.brand, data.last4)
    }


    private fun createCustomerSession(): CustomerSession {
        val customerSession = CustomerSession.getInstance()
        customerSession.retrieveCurrentCustomer(
                object : CustomerSession.CustomerRetrievalListener {
                    override fun onCustomerRetrieved(customer: Customer) {
                        Log.d("dddd", "onCustomerRetrieved")
                    }

                    override fun onError(
                            errorCode: Int,
                            errorMessage: String,
                            stripeError: StripeError?
                    ) {
                        Log.d("dddd", "onError")
                    }
                }
        )
        return customerSession
    }

    private fun initPaymentSession(customerSession: CustomerSession) {
        paymentSession.init(
                object : PaymentSession.PaymentSessionListener {
                    override fun onCommunicatingStateChanged(isCommunicating: Boolean) {
                        Log.d("dddd", "onCommunicatingStateChanged")
                    }


                    override fun onError(errorCode: Int, errorMessage: String) {
                        Log.d("dddd", "onError payment")
                    }

                    override fun onPaymentSessionDataChanged(data: PaymentSessionData) {
                        customerSession.retrieveCurrentCustomer(
                                object : CustomerSession.CustomerRetrievalListener {
                                    override fun onCustomerRetrieved(customer: Customer) {
                                        Log.d("onCustomerRetrieved", "onError")
                                    }

                                    override fun onError(
                                            errorCode: Int,
                                            errorMessage: String,
                                            stripeError: StripeError?
                                    ) {
                                        Log.d("onCustomerRetrieved", "onError")
                                    }

                                })
                    }
                }
        )
        paymentSession.setCartTotal(2000L)
    }

    fun hitPaymentUrl(token:String){
        val data=HashMap<String,String>()
        data["customer_id"] = getLoginInfo()!!.customerId!!
        data["stripe_token"] = token

        val webService = ApiHelper.createService2()
        webService.payment(data).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                response?.body()?.let {
                    //clientSecret = it.clientSecret!!
                    showDialog(getString(R.string.app_name),it.string())
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                t?.let {
                    showDialog(getString(R.string.app_name),it.localizedMessage)
                }
            }
        })
    }


}

class PaymentResultCallback(val activityRef: PaymentActivity) : ApiResultCallback<PaymentIntentResult> {
    override fun onSuccess(result: PaymentIntentResult) {
        activityRef.showLoading(false)
        val paymentIntent: StripeIntent = result.intent
        val status: StripeIntent.Status = paymentIntent.status!!
        if (status === StripeIntent.Status.Succeeded) {
            // Payment completed successfully
            activityRef.showToastMsg("Payment completed")
            activityRef.hitPaymentUrl(paymentIntent.paymentMethodId!!)
        } else if (status === StripeIntent.Status.RequiresPaymentMethod) {
            // Payment failed – allow retrying using a different payment method
            activityRef.showDialog("Payment","Payment failed – allow retrying using a different payment method")
        }
    }

    override fun onError(e: Exception) {
        activityRef.showLoading(false)
        activityRef.showToastMsg("${e.localizedMessage}")
    }

}
