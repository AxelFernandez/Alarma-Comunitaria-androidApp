package com.axelfernandez.alarmacomunitaria.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.axelfernandez.alarmacomunitaria.R
import com.axelfernandez.alarmacomunitaria.models.User

import com.google.android.material.snackbar.Snackbar

class ViewUtil {

    companion object{
        fun setSnackBar(v : View, color: Int,text:String){
            var snackbar : Snackbar = Snackbar.make(v,text,
                Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(ContextCompat.getColor(v.context, color))
            snackbar.show()
        }

        fun checkPermission(context : Activity) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) { //Can add more as per requirement
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    123
                )
            }
        }

        fun putUserToSharedPreferences(context: Context, new_user: User){
            var editor : SharedPreferences.Editor = context.getSharedPreferences("userSession", Context.MODE_PRIVATE).edit()
            editor.putString(context.getString(R.string.id),new_user.id)
            editor.putString(context.getString(R.string.email),new_user.email)
            editor.putString(context.getString(R.string.given_name),new_user.givenName)
            editor.putString(context.getString(R.string.family_name),new_user.familyName)
            editor.putString(context.getString(R.string.picture),new_user.photo)
            editor.putString(context.getString(R.string.phone),new_user.phone)
            editor.putString(context.getString(R.string.username),new_user.username)
            editor.putString(context.getString(R.string.address),new_user.address)
            editor.apply()


        }

        fun getUserFromSharedPreferences(context: Context):User{
            var sharedPreferences : SharedPreferences = context.getSharedPreferences("userSession", Context.MODE_PRIVATE)
            return User(
                email = sharedPreferences.getString(context.getString(R.string.email),null).toString(),
                givenName = sharedPreferences.getString(context.getString(R.string.given_name),null).toString(),
                familyName = sharedPreferences.getString(context.getString(R.string.family_name),null).toString(),
                photo = sharedPreferences.getString(context.getString(R.string.picture),null).toString(),
                phone = sharedPreferences.getString(context.getString(R.string.phone),null).toString(),
                username = sharedPreferences.getString(context.getString(R.string.username),null).toString(),
                address = sharedPreferences.getString(context.getString(R.string.address),null).toString(),
                id = sharedPreferences.getString(context.getString(R.string.id),null).toString()
            )
        }

        fun setIsCompletedRegistered(context: Context, isCompleted:Boolean){
            var editor : SharedPreferences.Editor = context.getSharedPreferences("userSession", Context.MODE_PRIVATE).edit()
            editor.putBoolean(context.getString(R.string.is_completed_registered),isCompleted)
            editor.apply()
        }
        fun getIsCompletedRegistered(context: Context):Boolean{
            var sharedPreferences : SharedPreferences = context.getSharedPreferences("userSession", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(context.getString(R.string.is_completed_registered),false)

        }
    }
}
