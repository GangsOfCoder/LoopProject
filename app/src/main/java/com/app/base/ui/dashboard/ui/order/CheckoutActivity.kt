package com.app.base.ui.dashboard.ui.order

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.base.R
import com.google.gson.GsonBuilder
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentConfiguration
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.StripeIntent
import kotlinx.android.synthetic.main.content_checkout.*
import java.lang.ref.WeakReference

//https://stripe.com/docs/payments/accept-a-payment#android
class CheckoutActivity : AppCompatActivity() {

    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_checkout)
        payButton.setOnClickListener {
            val params = cardInputWidget.paymentMethodCreateParams
            if (params != null) {
                val confirmParams = ConfirmPaymentIntentParams.createWithPaymentMethodCreateParams(params, paymentIntentClientSecret)
                stripe = Stripe(applicationContext, PaymentConfiguration.getInstance(applicationContext).publishableKey)
                stripe.confirmPayment(this, confirmParams)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val weakActivity = WeakReference<Activity>(this)

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, object : ApiResultCallback<PaymentIntentResult> {
            override fun onSuccess(result: PaymentIntentResult) {
                val paymentIntent = result.intent
                val status = paymentIntent.status
                if (status == StripeIntent.Status.Succeeded) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    //displayAlert(weakActivity.get(), "Payment succeeded", gson.toJson(paymentIntent), restartDemo = true)
                    showMessage("success")
                } else {
                    showMessage("failer")
                    //displayAlert(weakActivity.get(), "Payment failed", paymentIntent.lastPaymentError?.message ?: "")
                }
            }

            override fun onError(e: Exception) {

                //displayAlert(weakActivity.get(), "Payment failed", e.toString())
            }
        })
    }

    fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}

