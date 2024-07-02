package com.davay.android.feature.waitsession.domain

class WaitSessionOnBoardingInteractorImpl(
    private val repository: WaitSessionOnBoardingRepository
) : WaitSessionOnBoardingInteractror {
    override fun setFirstTimeLaunch() {
        repository.setFirstTimeLaunch()
    }

    override fun isFirstTimeLaunch(): Boolean {
        return repository.isFirstTimeLaunch()
    }
}