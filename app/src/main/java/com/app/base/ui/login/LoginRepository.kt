package com.app.base.ui.login

import com.app.base.api.service.ApiHelper
import com.app.base.data.login.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginRepository {
    private val webService = ApiHelper.createService()

    fun doLogin(successHandler: (LoginResponse) -> Unit,
                 failureHandler: (String) -> Unit,
                 onFailure: (Throwable) -> Unit,
                 data: HashMap<String,String>) {
        webService.doLogin(data).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
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

            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }

}