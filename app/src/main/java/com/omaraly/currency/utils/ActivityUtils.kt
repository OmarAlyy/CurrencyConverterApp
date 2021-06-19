package com.omaraly.currency.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, message, duration).show()
}

fun Activity.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(applicationContext, id)

