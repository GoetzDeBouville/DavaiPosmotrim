package com.davay.android.feature.splash.domain

class OnBoardingInteractorImpl(
    private val repository: OnBoardingRepository
): OnBoardingInteractror {
    override fun setIsFirstTime(isFirstTime: Boolean) {
        repository.setIsFirstTime(isFirstTime)
    }

    override fun getIsFirstTime(): Boolean {
        return repository.getIsFirstTime()
    }
}