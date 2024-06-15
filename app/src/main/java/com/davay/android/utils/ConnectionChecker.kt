package com.davay.android.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat

class ConnectionChecker(context: Context) {

    private val connectivityManager = ContextCompat.getSystemService(context, ConnectivityManager::class.java)

    val isConnected: Boolean
        get() {
            val activeNetwork = connectivityManager?.activeNetwork
            val networkCapabilities = connectivityManager?.getNetworkCapabilities(activeNetwork) ?: return false

            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
}
