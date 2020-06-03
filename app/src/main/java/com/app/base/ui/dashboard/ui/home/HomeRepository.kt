package com.app.base.ui.dashboard.ui.home

import com.app.base.api.service.ApiHelper
import com.app.base.data.forgot.ForgetResponse
import com.app.base.data.streets.StreetResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object HomeRepository {
    private val webService = ApiHelper.createService2()

    fun getStreets(successHandler: (StreetResponse) -> Unit,
                       failureHandler: (String) -> Unit,
                       onFailure: (Throwable) -> Unit) {
        webService.getStreets().enqueue(object : Callback<StreetResponse> {
            override fun onResponse(call: Call<StreetResponse>?, response: Response<StreetResponse>?) {
                response?.body()?.let {
                    if (it.status == 0) {
                        failureHandler(it.message!!)
                        return
                    }
                    successHandler(it)
                }
            }

            override fun onFailure(call: Call<StreetResponse>?, t: Throwable?) {
                t?.let {
                    onFailure(it)
                }
            }
        })
    }
}