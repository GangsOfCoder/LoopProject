package com.app.base.data.register

import com.google.gson.annotations.SerializedName

data class EmailVerifyResponse(

	@field:SerializedName("otp")
	val otp: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)