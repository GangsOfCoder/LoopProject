package com.app.base.data.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("token")
	val token: String? = null
)


data class Data(

		@field:SerializedName("device_model")
		val deviceModel: String? = null,

		@field:SerializedName("os_version")
		val osVersion: String? = null,

		@field:SerializedName("created_at")
		val createdAt: String? = null,

		@field:SerializedName("country_code")
		val countryCode: String? = null,

		@field:SerializedName("account_id")
		val accountId: String? = null,

		@field:SerializedName("device_manufacture")
		val deviceManufacture: String? = null,

		@field:SerializedName("device_token")
		val deviceToken: String? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("country_name")
		val countryName: String? = null,

		@field:SerializedName("os_name")
		val osName: String? = null,

		@field:SerializedName("phone_number")
		val phoneNumber: String? = null,

		@field:SerializedName("id")
		val id: String? = null,

		@field:SerializedName("model_number")
		val modelNumber: String? = null,

		@field:SerializedName("customer_id")
		val customerId: String? = null,

		@field:SerializedName("email")
		val email: String? = null
)