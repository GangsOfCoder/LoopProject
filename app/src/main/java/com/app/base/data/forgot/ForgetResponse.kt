package com.app.base.data.forgot

import com.google.gson.annotations.SerializedName

data class ForgetResponse(

        @field:SerializedName("otp")
        val otp: String? = null,

        @field:SerializedName("message")
        val message: String? = null,

        @field:SerializedName("status")
        val status: Int? = null,

        @field:SerializedName("token")
        val token: String? = null
)