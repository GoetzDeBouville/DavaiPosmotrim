package com.davay.android.feature.splash.domain

interface FirstTimeFlagStorage {
    fun isFirstTimeLaunch(): Boolean
    fun markFirstTimeLaunch()
}