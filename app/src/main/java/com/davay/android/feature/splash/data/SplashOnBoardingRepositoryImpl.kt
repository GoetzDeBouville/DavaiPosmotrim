package com.davay.android.feature.splash.data

import com.davay.android.feature.splash.domain.FirstTimeFlagStorage
import com.davay.android.feature.splash.domain.SplashOnBoardingRepository

class SplashOnBoardingRepositoryImpl(
    private val firstTimeFlagStorage: FirstTimeFlagStorage
) : SplashOnBoardingRepository {
    override fun isFirstTimeLaunch(): Boolean {
        return firstTimeFlagStorage.isFirstTimeLaunch()
    }

    override fun markFirstTimeLaunch() {
        firstTimeFlagStorage.markFirstTimeLaunch()
    }
}