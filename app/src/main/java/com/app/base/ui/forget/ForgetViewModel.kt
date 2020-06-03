package com.app.base.ui.forget

import androidx.lifecycle.MutableLiveData
import com.app.base.api.model.MyViewModel
import com.app.base.data.forgot.ForgetResponse

class ForgetViewModel : MyViewModel() {
    var response = MutableLiveData<ForgetResponse>()

    fun sendCodeOnMail(data: HashMap<String,String>) {
        isLoading.value = true
        ForgetRepository.sendCodeOnMail({
            response.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        }, {
            onFailure.value = it
            isLoading.value = false
        }, data)
    }

    fun newPasswordToken(data: HashMap<String, String>, token: String) {
        isLoading.value = true
        ForgetRepository.changePassword({
            response.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        }, {
            onFailure.value = it
            isLoading.value = false
        }, data,token)
    }
}
