package com.davay.android.feature.splash.domain

interface OnBoardingRepository {
    fun isFirstTimeLaunch(): Boolean
    fun setFirstTimeLaunch()
}