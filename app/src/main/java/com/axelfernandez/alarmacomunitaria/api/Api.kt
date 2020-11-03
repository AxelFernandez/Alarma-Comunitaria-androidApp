package com.axelfernandez.deliverylavalle.api

import com.axelfernandez.alarmacomunitaria.models.*
import retrofit2.Call;
import retrofit2.http.*

interface Api {

    @Headers("Content-Type: application/json")
    @POST("registry")
    fun loginWithGoogle(@Body userData: User):Call<UserResponse>

    @POST("update_info")
    fun updateAddress(@Body data: InfoPlus):Call<ResponseBoolean>

    @POST("send_alarm")
    fun sendAlarm(@Body alarm: Alarm):Call<ResponseBoolean>

}