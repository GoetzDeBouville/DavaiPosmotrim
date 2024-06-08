package com.davay.android.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun checkNetworkState(connectivityManager: ConnectivityManager?): Boolean {
    val activeNetwork = connectivityManager?.activeNetwork
    val networkCapabilities = connectivityManager?.getNetworkCapabilities(activeNetwork) ?: return false

    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
}