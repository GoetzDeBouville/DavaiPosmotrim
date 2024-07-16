package com.davay.android.feature.splash.domain

import com.davay.android.app.core.feature.introduction.domain.FirstTimeFlagRepository

class SplashOnBoardingInteractorImpl(
    private val repository: FirstTimeFlagRepository
) : SplashOnBoardingInteractror {
    override fun markFirstTimeLaunch() {
        repository.markFirstTimeLaunch()
    }

    override fun isFirstTimeLaunch(): Boolean {
        return repository.isFirstTimeLaunch()
    }
}