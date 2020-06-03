package com.app.base.ui.forget

import com.app.base.api.service.ApiHelper
import com.app.base.data.forgot.ForgetResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ForgetRepository {
    private val webService = ApiHelper.createService()

    fun sendCodeOnMail(successHandler: (ForgetResponse) -> Unit,
                       failureHandler: (String) -> Unit,
                       onFailure: (Throwable) -> Unit,
                       data: HashMap<String, String>) {
        webService.sendCodeOnMail(data).enqueue(object : Callback<ForgetResponse> {
            override fun onResponse(call: Call<ForgetResponse>?, response: Response<ForgetResponse>?) {
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

            override fun onFailure(call: Call<ForgetResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

    fun changePassword(successHandler: (ForgetResponse) -> Unit,
                       failureHandler: (String) -> Unit,
                       onFailure: (Throwable) -> Unit,
                       data: HashMap<String, String>,token:String) {
        webService.changePassword(token, data).enqueue(object : Callback<ForgetResponse> {
            override fun onResponse(call: Call<ForgetResponse>?, response: Response<ForgetResponse>?) {
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

            override fun onFailure(call: Call<ForgetResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

}