package com.davay.android.feature.waitsession.domain

interface WaitSessionOnBoardingInteractor {
    fun markFirstTimeLaunch()
    fun isFirstTimeLaunch(): Boolean
}