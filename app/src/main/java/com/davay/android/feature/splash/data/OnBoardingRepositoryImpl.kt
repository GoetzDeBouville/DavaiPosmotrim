package com.davay.android.feature.splash.data

import com.davay.android.feature.splash.domain.OnBoardingRepository

class OnBoardingRepositoryImpl(
    private val isFirstTimeStorage: IsFirstTimeStorage
): OnBoardingRepository {
    override fun getIsFirstTime(): Boolean {
        return isFirstTimeStorage.getIsFirstTime()
    }

    override fun setIsFirstTime(isFirstTime: Boolean) {
        isFirstTimeStorage.setIsFirstTime(isFirstTime)
    }
}