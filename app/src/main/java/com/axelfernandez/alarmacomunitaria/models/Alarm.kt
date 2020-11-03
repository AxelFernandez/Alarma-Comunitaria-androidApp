package com.axelfernandez.alarmacomunitaria.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Alarm(
    var location: String,
    var alarmType: String,
    var userId: String

)
data class Location(
    val lat :String,
    val long :String)

