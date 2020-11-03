package com.axelfernandez.alarmacomunitaria.ui.Information

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.alarmacomunitaria.api.RetrofitFactory
import com.axelfernandez.alarmacomunitaria.models.*
import com.axelfernandez.alarmacomunitaria.repository.LoginRepository
import com.axelfernandez.alarmacomunitaria.repository.UserExtensionRepository
import com.axelfernandez.deliverylavalle.api.Api

class InformationViewModel : ViewModel() {
    private val UserExtensionRepository = UserExtensionRepository(RetrofitFactory.buildService(Api::class.java))


    fun registryUserInfo(info: InfoPlus){
        UserExtensionRepository.registryInfo(info)
    }
    fun returnResponse(): LiveData<ResponseBoolean> {
        return UserExtensionRepository.returnData()
    }

}