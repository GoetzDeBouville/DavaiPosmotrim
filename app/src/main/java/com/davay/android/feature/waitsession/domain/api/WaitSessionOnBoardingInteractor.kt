package com.davay.android.feature.waitsession.domain.api

interface WaitSessionOnBoardingInteractor {
    fun markFirstTimeLaunch()
    fun isFirstTimeLaunch(): Boolean
}