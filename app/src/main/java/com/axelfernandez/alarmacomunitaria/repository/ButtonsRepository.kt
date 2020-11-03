package com.axelfernandez.alarmacomunitaria.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.axelfernandez.alarmacomunitaria.models.Alarm
import com.axelfernandez.alarmacomunitaria.models.ResponseBoolean
import com.axelfernandez.deliverylavalle.api.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ButtonsRepository (
    private val api : Api
) {
    val data = MutableLiveData<ResponseBoolean>()

    fun sendAlarm(alarm: Alarm): MutableLiveData<ResponseBoolean> {
        api.sendAlarm(alarm).enqueue(object : Callback<ResponseBoolean> {
            override fun onFailure(call: Call<ResponseBoolean>, t: Throwable) {
                data.value = null
            }

            override fun onResponse(call: Call<ResponseBoolean>, response: Response<ResponseBoolean>) {
                data.value = response.body()
                data.postValue(response.body())

            }
        })
        return data
    }

    fun returnData(): LiveData<ResponseBoolean> {
        return data
    }
}