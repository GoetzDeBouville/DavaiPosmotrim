package com.davay.android.feature.splash.data

import com.davay.android.app.core.feature.introduction.domain.FirstTimeFlagRepository
import com.davay.android.app.core.feature.introduction.domain.FirstTimeFlagStorage

class SplashOnBoardingRepositoryImpl(
    private val firstTimeFlagStorage: FirstTimeFlagStorage
) : FirstTimeFlagRepository {
    override fun isFirstTimeLaunch(): Boolean {
        return firstTimeFlagStorage.isFirstTimeLaunch()
    }

    override fun markFirstTimeLaunch() {
        firstTimeFlagStorage.markFirstTimeLaunch()
    }
}