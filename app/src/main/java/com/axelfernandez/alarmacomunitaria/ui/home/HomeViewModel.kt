package com.axelfernandez.alarmacomunitaria.ui.home

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.axelfernandez.alarmacomunitaria.R
import com.axelfernandez.alarmacomunitaria.api.RetrofitFactory
import com.axelfernandez.alarmacomunitaria.models.*
import com.axelfernandez.alarmacomunitaria.repository.ButtonsRepository
import com.axelfernandez.alarmacomunitaria.utils.MyFirebaseMessageService
import com.axelfernandez.alarmacomunitaria.utils.ViewUtil
import com.axelfernandez.deliverylavalle.api.Api
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class HomeViewModel : ViewModel() {

    private val alarmRepository = ButtonsRepository(RetrofitFactory.buildService(Api::class.java))


    fun suscribeToTopic(v :View){
         MyFirebaseMessageService.getToken()

        Firebase.messaging.subscribeToTopic("alarms")
            .addOnCompleteListener { task ->
                var msg = "Estás Recibiendo las Alarmas"
                if (!task.isSuccessful) {
                    msg = "Hubo un problema al escuchar Alarmas"
                }
                Log.d(TAG, msg)
            }
    }

    private fun sendAlarm(alarm: Alarm){
        alarmRepository.sendAlarm(alarm)
    }
    fun returnResponse(): LiveData<ResponseBoolean> {
        return alarmRepository.returnData()
    }

    fun getLocationAndSendAlarm(activity: Activity, v : View, type:String){
        var fusedLocationClient : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        ViewUtil.checkPermission(activity)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            var location = Location(location.latitude.toString(),location.longitude.toString())
            ViewUtil.setSnackBar(v, R.color.colorAccent,"Ubicacion recuperada, enviando Alarma")
            val locationString = "%s, %s".format(location.lat,location.long)
            val user = ViewUtil.getUserFromSharedPreferences(activity)
            val alarm = Alarm(locationString,type,user.id!!)
            sendAlarm(alarm)
            Log.e("LOCATION", "Get location successful!")

        }.addOnFailureListener {
            Log.e("LOCATION", it.message!!)
            ViewUtil.checkPermission(context = activity);
            ViewUtil.setSnackBar(v, R.color.red,"No hay Permisos para acceder a la Ubicacion, revisalos!")
            return@addOnFailureListener
        }

    }
}