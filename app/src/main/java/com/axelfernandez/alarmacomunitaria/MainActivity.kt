package com.axelfernandez.alarmacomunitaria

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import com.axelfernandez.alarmacomunitaria.utils.ViewUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        delegate.applyDayNight()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ViewUtil.checkPermission(context = this);
        }
    }
}