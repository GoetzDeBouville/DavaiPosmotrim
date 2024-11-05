package com.davay.android.feature.waitsession.domain

import com.davay.android.core.domain.lounchcontrol.api.FirstTimeFlagRepository
import com.davay.android.feature.waitsession.domain.api.WaitSessionOnBoardingInteractor

class WaitSessionOnBoardingInteractorImpl(
    private val repository: FirstTimeFlagRepository
) : WaitSessionOnBoardingInteractor {
    override fun markFirstTimeLaunch() {
        repository.markFirstTimeLaunch()
    }

    override fun isFirstTimeLaunch(): Boolean {
        return repository.isFirstTimeLaunch()
    }
}