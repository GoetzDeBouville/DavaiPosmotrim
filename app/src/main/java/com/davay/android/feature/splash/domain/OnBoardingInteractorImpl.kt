package com.davay.android.feature.splash.domain


class OnBoardingInteractorImpl(
    private val repository: OnBoardingRepository
) : OnBoardingInteractror {
    override fun setFirstTimeLaunch() {
        repository.setFirstTimeLaunch()
    }

    override fun isFirstTimeLaunch(): Boolean {
        return repository.isFirstTimeLaunch()
    }
}