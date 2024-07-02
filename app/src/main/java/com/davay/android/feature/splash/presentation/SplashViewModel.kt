package com.davay.android.feature.splash.presentation

import com.davay.android.base.BaseViewModel
import com.davay.android.feature.splash.domain.SplashOnBoardingInteractror
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val splashOnBoardingInteractror: SplashOnBoardingInteractror
) : BaseViewModel() {
    fun isFirstTimeLaunch(): Boolean {
        return splashOnBoardingInteractror.isFirstTimeLaunch()
    }

    fun setFirstTimeLaunch() {
        splashOnBoardingInteractror.setFirstTimeLaunch()
    }
}