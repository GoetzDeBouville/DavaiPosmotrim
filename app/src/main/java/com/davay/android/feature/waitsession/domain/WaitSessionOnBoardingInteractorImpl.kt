package com.davay.android.feature.waitsession.domain

class WaitSessionOnBoardingInteractorImpl(
    private val repository: WaitSessionOnBoardingRepository
) : WaitSessionOnBoardingInteractor {
    override fun markFirstTimeLaunch() {
        repository.markFirstTimeLaunch()
    }

    override fun isFirstTimeLaunch(): Boolean {
        return repository.isFirstTimeLaunch()
    }
}