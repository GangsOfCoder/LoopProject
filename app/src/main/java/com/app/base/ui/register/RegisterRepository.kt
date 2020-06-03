package com.app.base.ui.register

import com.app.base.api.service.ApiHelper
import com.app.base.data.register.EmailVerifyResponse
import com.app.base.data.register.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RegisterRepository {
    private val webService = ApiHelper.createService()

    fun doRegister(successHandler: (RegisterResponse) -> Unit,
                   failureHandler: (String) -> Unit,
                   onFailure: (Throwable) -> Unit,
                   request: HashMap<String,String>) {
        webService.doRegister(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>?) {
                response?.body()?.let {
                    if (it.status == 0) {
                        failureHandler(it.message!!)
                        return
                    }
                    successHandler(it)
                }
                if (response?.code() == 422) {
                    response.errorBody()?.let {
                        val error = ApiHelper.handleAuthenticationError(response.errorBody()!!)
                        failureHandler(error)
                    }

                } else {
                    response?.errorBody()?.let {
                        val error = ApiHelper.handleApiError(response.errorBody()!!)
                        failureHandler(error)
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

    fun sendEmailVerificationCode(successHandler: (EmailVerifyResponse) -> Unit,
                                  failureHandler: (String) -> Unit,
                                  onFailure: (Throwable) -> Unit,
                                  data: HashMap<String,String>) {
        webService.sendEmailVerificationCode(data).enqueue(object : Callback<EmailVerifyResponse> {
            override fun onResponse(call: Call<EmailVerifyResponse>?, response: Response<EmailVerifyResponse>?) {
                response?.body()?.let {

                    if (it.status == 0) {
                        failureHandler(it.message!!)
                        return
                    }
                    successHandler(it)
                }
                if (response?.code() == 422) {
                    response.errorBody()?.let {
                        val error = ApiHelper.handleAuthenticationError(response.errorBody()!!)
                        failureHandler(error)
                    }

                } else {
                    response?.errorBody()?.let {
                        val error = ApiHelper.handleApiError(response.errorBody()!!)
                        failureHandler(error)
                    }
                }
            }

            override fun onFailure(call: Call<EmailVerifyResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

}