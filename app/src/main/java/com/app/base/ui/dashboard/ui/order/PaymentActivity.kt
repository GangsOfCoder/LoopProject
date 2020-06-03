package com.app.base.ui.dashboard.ui.order

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.base.R
import com.app.base.base.BaseActivity
import com.stripe.android.view.AddPaymentMethodActivityStarter
import com.stripe.android.view.PaymentMethodsActivityStarter
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        attachClickListeners()
    }

    private fun attachClickListeners() {
        ivToolbarBack.setOnClickListener {
            onBackPressed()
        }

        btnPaymentMethod.setOnClickListener {
            PaymentMethodsActivityStarter(this).startForResult()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PaymentMethodsActivityStarter.REQUEST_CODE && data != null) {
            val result = PaymentMethodsActivityStarter.Result.fromIntent(data)
            val paymentMethod = result?.paymentMethod

            // use paymentMethod
        }
    }
}
