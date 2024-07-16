package com.davay.android.feature.waitsession.data

import com.davay.android.feature.splash.domain.FirstTimeFlagStorage
import com.davay.android.feature.waitsession.domain.WaitSessionOnBoardingRepository

class WaitSessionOnBoardingRepositoryImpl(
    private val firstTimeFlagStorage: FirstTimeFlagStorage
) : WaitSessionOnBoardingRepository {
    override fun isFirstTimeLaunch(): Boolean {
        return firstTimeFlagStorage.isFirstTimeLaunch()
    }

    override fun markFirstTimeLaunch() {
        firstTimeFlagStorage.markFirstTimeLaunch()
    }
}