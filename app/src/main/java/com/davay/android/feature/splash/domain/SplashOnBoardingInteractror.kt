package com.davay.android.feature.splash.domain

interface SplashOnBoardingInteractror {
    fun setFirstTimeLaunch()
    fun isFirstTimeLaunch(): Boolean
}