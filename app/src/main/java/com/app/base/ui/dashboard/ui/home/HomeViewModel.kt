package com.app.base.ui.dashboard.ui.home

import androidx.lifecycle.MutableLiveData
import com.app.base.api.model.MyViewModel
import com.app.base.data.forgot.ForgetResponse
import com.app.base.data.streets.StreetResponse

class HomeViewModel : MyViewModel() {
    var response = MutableLiveData<StreetResponse>()

    fun getStreets() {
        isLoading.value = true
        HomeRepository.getStreets({
            response.value = it
            isLoading.value = false
        }, {
            apiError.value = it
            isLoading.value = false
        }, {
            onFailure.value = it
            isLoading.value = false
        })
    }


}
