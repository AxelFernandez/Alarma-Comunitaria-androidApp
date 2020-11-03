package com.axelfernandez.alarmacomunitaria.models

data class User(
    var id: String?,
    var username: String?,
    val email: String,
    val givenName: String,
    val familyName: String,
    val photo: String,
    var phone: String?,
    var address: String?)


data class UserResponse(
    var isNew: Boolean,
    var isCompletedRegistered: Boolean,
    var clientId: String,
    val username: String,
    val address:String?,
    val phone:String?
    )

data class InfoPlus(
    var phone: String,
    var address: String,
    var userId: String
)
data class ResponseBoolean(
    var done: Boolean
)
