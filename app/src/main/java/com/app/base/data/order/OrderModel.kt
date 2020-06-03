package com.app.base.data.order

import java.io.Serializable

data class OrderModel(
        var user_id: String = "",
        var recycle_bin_id: String = "",
        var user_name: String = "",
        var phone_number: String = "",
        var user_email: String = "",
        var street_number: String = "",
        var building_name: String = "",
        var floor_number: String = "",
        var flat_number: String = "",
        var subscription_id: String = "",
        var payment_mode: String = ""
) : Serializable