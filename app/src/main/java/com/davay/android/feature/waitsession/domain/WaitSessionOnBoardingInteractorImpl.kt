package com.davay.android.feature.waitsession.domain

import com.davay.android.app.core.feature.introduction.domain.FirstTimeFlagRepository

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