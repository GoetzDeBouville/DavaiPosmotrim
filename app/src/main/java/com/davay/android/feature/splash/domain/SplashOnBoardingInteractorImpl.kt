package com.davay.android.feature.splash.domain


class SplashOnBoardingInteractorImpl(
    private val repository: SplashOnBoardingRepository
) : SplashOnBoardingInteractror {
    override fun markFirstTimeLaunch() {
        repository.markFirstTimeLaunch()
    }

    override fun isFirstTimeLaunch(): Boolean {
        return repository.isFirstTimeLaunch()
    }
}