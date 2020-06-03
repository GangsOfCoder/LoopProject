package com.app.base.ui.register

import androidx.lifecycle.MutableLiveData
import com.app.base.api.model.MyViewModel
import com.app.base.data.register.EmailVerifyResponse
import com.app.base.data.register.RegisterResponse

class RegisterViewModel : MyViewModel() {
    var response = MutableLiveData<RegisterResponse>()

    var emailVerifyResponse = MutableLiveData<EmailVerifyResponse>()
    fun doRegister(data:HashMap<String,String>) {
        isLoading.value = true
        RegisterRepository.doRegister({
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

    fun sendEmailVerificationCode(data: HashMap<String, String>) {
        isLoading.value = true
        RegisterRepository.sendEmailVerificationCode({
            emailVerifyResponse.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        }, {
            onFailure.value = it
            isLoading.value = false
        }, data)
    }
}
