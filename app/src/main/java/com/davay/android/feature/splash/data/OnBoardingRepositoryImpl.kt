package com.davay.android.feature.splash.data

import com.davay.android.feature.splash.domain.OnBoardingRepository

class OnBoardingRepositoryImpl(
    private val firstTimeFlagStorage: FirstTimeFlagStorage
) : OnBoardingRepository {
    override fun isFirstTimeLaunch(): Boolean {
        return firstTimeFlagStorage.isFirstTimeLaunch()
    }

    override fun setFirstTimeLaunch() {
        firstTimeFlagStorage.setFirstTimeLaunch()
    }
}