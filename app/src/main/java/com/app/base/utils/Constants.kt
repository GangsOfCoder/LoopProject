package com.app.base.utils


object Constants {

    //const val BASE_URL = "http://ec2-18-223-118-166.us-east-2.compute.amazonaws.com/loop/index.php/api/"
    const val BASE_URL = "http://ec2-3-18-70-105.us-east-2.compute.amazonaws.com/loop/index.php/api/"
    const val BASE_URL2 = "http://theloopshk.com:3000/api/"

    const val TOKEN = "token"
    const val PERMISSION_READ_EXTERNAL_STORAGE = 123
    const val PERMISSION_REQUEST_CODE = 98
    const val LOCATION_PERMISSION_REQUEST_CODE = 101
    const val HANDLER_DELAY_TIME:Long=2000

    const val NOTI_COUNT = "noti_count"


    const val APP_HIDDEN_FOLDER="/.bestlkjtyme"


    internal interface httpcodes {
        companion object {
            val STATUS_OK = 200
            val STATUS_BAD_REQUEST = 400
            val STATUS_SESSION_EXPIRED = 401
            val STATUS_PLAN_EXPIRED = 403
            val STATUS_VALIDATION_ERROR = 404
            val STATUS_SERVER_ERROR = 500
            val STATUS_UNKNOWN_ERROR = 503
            val STATUS_API_VALIDATION_ERROR = 422
        }
    }

    const val IS_LOGOUT = "is_logout"

    //FAILURE MESSAGES
    const val SOMETHING_WENT_WRONG = "Something went wrong please try again later!"
    const val FAILURE_TIME_OUT_ERROR = "Request time out!"
    const val FAILURE_SOMETHING_WENT_WRONG = "Something went wrong please try again later!"
    const val FAILURE_SERVER_NOT_RESPONDING = "Oops! looks like we are having internal problems. Please try again later."
    const val FAILURE_INTERNET_CONNECTION = "Internet connection appears to be offline. Please check your network settings."


}