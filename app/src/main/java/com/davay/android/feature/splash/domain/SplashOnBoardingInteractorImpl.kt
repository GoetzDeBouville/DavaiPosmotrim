package com.davay.android.feature.splash.domain


class SplashOnBoardingInteractorImpl(
    private val repository: SplashOnBoardingRepository
) : SplashOnBoardingInteractror {
    override fun setFirstTimeLaunch() {
        repository.setFirstTimeLaunch()
    }

    override fun isFirstTimeLaunch(): Boolean {
        return repository.isFirstTimeLaunch()
    }
}