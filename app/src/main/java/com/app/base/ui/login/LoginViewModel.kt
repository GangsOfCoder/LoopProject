package com.app.base.ui.login

import androidx.lifecycle.MutableLiveData
import com.app.base.api.model.MyViewModel
import com.app.base.data.login.LoginResponse
import com.app.base.ui.register.RegisterRepository

class LoginViewModel : MyViewModel() {
    var response = MutableLiveData<LoginResponse>()

    fun doLogin(data: HashMap<String,String>) {
        isLoading.value = true
        LoginRepository.doLogin({
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
}
