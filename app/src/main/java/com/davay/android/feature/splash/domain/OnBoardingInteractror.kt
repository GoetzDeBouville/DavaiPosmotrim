package com.davay.android.feature.splash.domain

interface OnBoardingInteractror {
    fun setIsFirstTime(isFirstTime: Boolean)
    fun getIsFirstTime(): Boolean
}