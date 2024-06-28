package com.davay.android.feature.splash.data

import android.content.SharedPreferences

class IsFirstTimeStorage(
    private val sharedPreferences: SharedPreferences
) {
        fun getIsFirstTime(): Boolean {
            return sharedPreferences.getBoolean(IS_FIRST_TIME, false)
        }

        fun setIsFirstTime(isFirstTime: Boolean) {
            sharedPreferences.edit()
                .putBoolean(IS_FIRST_TIME, isFirstTime)
                .apply()
        }

        companion object {
            const val IS_FIRST_TIME = "isFirstTime"
        }
}