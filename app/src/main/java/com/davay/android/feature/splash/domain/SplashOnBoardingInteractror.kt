package com.davay.android.feature.splash.domain

interface SplashOnBoardingInteractror {
    fun markFirstTimeLaunch()
    fun isFirstTimeLaunch(): Boolean
}