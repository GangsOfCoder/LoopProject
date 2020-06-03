package com.app.base.application

import Preferences
import android.app.Application
import android.content.Context
import com.app.base.R
import com.stripe.android.PaymentConfiguration


class Application : Application() {

    var mContext: Context? = null


    companion object AppContext {
        lateinit var instance: com.app.base.application.Application
         fun getContext(): Context {
            return instance
        }
    }

    init {
        instance = this
    }


    override fun onCreate() {
        super.onCreate()
        Preferences.initPreferences(this)
        mContext = applicationContext
        PaymentConfiguration.init(
                applicationContext,
                getString(R.string.stripe_publishable_test_key)
        )
    }


}
