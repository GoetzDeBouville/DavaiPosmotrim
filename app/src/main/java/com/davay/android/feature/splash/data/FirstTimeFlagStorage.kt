package com.davay.android.feature.splash.data

import android.content.SharedPreferences

class FirstTimeFlagStorage(
    private val sharedPreferences: SharedPreferences
) {
    fun isFirstTimeLaunch(): Boolean {
        return sharedPreferences.getBoolean(FIRST_TIME_LAUNCH_KEY, true)
    }

    fun setFirstTimeLaunch() {
        sharedPreferences.edit().putBoolean(FIRST_TIME_LAUNCH_KEY, false).apply()
    }

    private companion object {
        const val FIRST_TIME_LAUNCH_KEY = "isFirstTimeLaunchKey"
    }
}