package com.app.base.data.streets

import com.google.gson.annotations.SerializedName

data class StreetResponse(

        @field:SerializedName("data")
        val data: List<StreetItem?>? = null,

        @field:SerializedName("message")
        val message: String? = null,

        @field:SerializedName("status")
        val status: Int? = null
)

data class StreetItem(

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("id")
        val id: Int? = null
)