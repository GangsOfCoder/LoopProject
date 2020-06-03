package com.app.base.api.service

import com.app.base.data.forgot.ForgetResponse
import com.app.base.data.login.LoginResponse
import com.app.base.data.register.EmailVerifyResponse
import com.app.base.data.register.RegisterResponse
import com.app.base.data.streets.StreetResponse
import retrofit2.Call
import retrofit2.http.*


interface WebService {

    @Headers("Accept: " + "application/json")
    @FormUrlEncoded
    @POST("register")
    fun doRegister(@FieldMap data: HashMap<String, String>): Call<RegisterResponse>

    @Headers("Accept: " + "application/json")
    @FormUrlEncoded
    @POST("login")
    fun doLogin(@FieldMap data: HashMap<String, String>): Call<LoginResponse>


    @Headers("Accept: " + "application/json")
    @FormUrlEncoded
    @POST("emailVerify")
    fun sendEmailVerificationCode(@FieldMap data: HashMap<String, String>): Call<EmailVerifyResponse>

    @Headers("Accept: " + "application/json")
    @FormUrlEncoded
    @POST("forget")
    fun sendCodeOnMail(@FieldMap data: HashMap<String, String>): Call<ForgetResponse>


    @Headers("Accept: " + "application/json")
    @FormUrlEncoded
    @POST("changePassword")
    fun changePassword(@Header("token") token: String, @FieldMap data: HashMap<String, String>): Call<ForgetResponse>

    @Headers("Accept: " + "application/json")
    @GET("get_streets")
    fun getStreets(): Call<StreetResponse>


}