package com.davay.android.feature.splash.data

import android.content.SharedPreferences
import com.davay.android.core.domain.lounchcontrol.api.FirstTimeFlagStorage

class SplashStorageImpl(
    private val sharedPreferences: SharedPreferences
) : FirstTimeFlagStorage {
    override fun isFirstTimeLaunch(): Boolean {
        return sharedPreferences.getBoolean(FIRST_TIME_FLAG_FOR_SPLASH_KEY, true)
    }

    override fun markFirstTimeLaunch() {
        sharedPreferences.edit().putBoolean(FIRST_TIME_FLAG_FOR_SPLASH_KEY, false).apply()
    }

    private companion object {
        const val FIRST_TIME_FLAG_FOR_SPLASH_KEY = "isFirstTimeSplashIntroduction"
    }
}