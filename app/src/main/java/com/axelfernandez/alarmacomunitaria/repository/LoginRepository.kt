package com.axelfernandez.alarmacomunitaria.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.axelfernandez.alarmacomunitaria.models.User
import com.axelfernandez.alarmacomunitaria.models.UserResponse
import com.axelfernandez.deliverylavalle.api.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository (
    private val api : Api
) {
    val data = MutableLiveData<UserResponse>()

    fun registryUser(user: User): MutableLiveData<UserResponse> {
        api.loginWithGoogle(user).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                data.value = response.body()
                data.postValue(response.body())

            }
        })
        return data
    }

    fun returnData(): LiveData<UserResponse> {
        return data
    }
}