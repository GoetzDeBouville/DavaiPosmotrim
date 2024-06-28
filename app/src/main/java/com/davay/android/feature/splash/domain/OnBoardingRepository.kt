package com.davay.android.feature.splash.domain

interface OnBoardingRepository {
    fun getIsFirstTime(): Boolean
    fun setIsFirstTime(isFirstTime: Boolean)
}