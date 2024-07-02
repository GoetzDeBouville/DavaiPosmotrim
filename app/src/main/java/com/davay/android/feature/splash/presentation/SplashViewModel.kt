package com.davay.android.feature.splash.presentation

import com.davay.android.base.BaseViewModel
import com.davay.android.feature.splash.domain.OnBoardingInteractror
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val onBoardingInteractror: OnBoardingInteractror
) : BaseViewModel() {
    fun isFirstTimeLaunch(): Boolean {
        return onBoardingInteractror.isFirstTimeLaunch()
    }

    fun setFirstTimeLaunch() {
        onBoardingInteractror.setFirstTimeLaunch()
    }
}