package com.blazze.albumapp.utils

import android.content.Context
import android.net.ConnectivityManager

object AppUtils {

    fun Context.isNetworkAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        return activeNetwork != null
    }

}