package com.davay.android.feature.splash.domain

interface SplashOnBoardingRepository {
    fun isFirstTimeLaunch(): Boolean
    fun markFirstTimeLaunch()
}