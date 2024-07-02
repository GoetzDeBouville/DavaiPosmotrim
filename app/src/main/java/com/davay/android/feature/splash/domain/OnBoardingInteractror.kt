package com.davay.android.feature.splash.domain

interface OnBoardingInteractror {
    fun setFirstTimeLaunch()
    fun isFirstTimeLaunch(): Boolean
}