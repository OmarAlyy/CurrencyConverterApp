package com.omaraly.currency.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.omaraly.currency.utils.isNight
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class CurrencyApp : Application() {

    override fun onCreate() {
        super.onCreate()
         val mode = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }
}
