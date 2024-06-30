package com.davay.android.feature.splash.viewmodel

import android.util.Log
import com.davay.android.base.BaseViewModel
import com.davay.android.feature.splash.domain.OnBoardingInteractror
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val onBoardingInteractror: OnBoardingInteractror
) : BaseViewModel() {
    fun getIsFirstTime(): Boolean {
        Log.d("TAG", onBoardingInteractror.getIsFirstTime().toString())
        return onBoardingInteractror.getIsFirstTime()
    }

    fun setIsFirstTime(isFirstTime: Boolean) {
        onBoardingInteractror.setIsFirstTime(isFirstTime)
    }
}