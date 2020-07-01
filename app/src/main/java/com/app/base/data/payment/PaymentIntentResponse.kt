package com.app.base.data.payment

import com.google.gson.annotations.SerializedName

data class PaymentIntentResponse(

	@field:SerializedName("success")
	val success: Int? = null,

	@field:SerializedName("clientSecret")
	val clientSecret: String? = null
)
