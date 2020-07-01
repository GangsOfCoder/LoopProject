package com.app.base.api.service

import android.util.Log
import com.stripe.android.EphemeralKeyProvider
import com.stripe.android.EphemeralKeyUpdateListener
import kotlinx.coroutines.*


internal class ExampleEphemeralKeyProvider(private val customerId: String?) : EphemeralKeyProvider {

    private val workScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val webService = ApiHelper.createService2()

    override fun createEphemeralKey(apiVersion: String, keyUpdateListener: EphemeralKeyUpdateListener) {

        workScope.launch {
            val response =
                    kotlin.runCatching {
                        webService
                                .createEphemeralKey(hashMapOf("api_version" to "2015-10-12", "customer_id" to customerId.toString()))
                                .string()
                    }

            withContext(Dispatchers.Main) {
                response.fold(
                        onSuccess = {
                            Log.d("mmmmm", it.toString())
                            keyUpdateListener.onKeyUpdate(it)
                        },
                        onFailure = {
                            Log.d("mmmmm", it.toString())
                            keyUpdateListener
                                    .onKeyUpdateFailure(0, it.message.orEmpty())
                        }
                )
            }
        }
    }
}