package com.davay.android.feature.waitsession.domain

interface WaitSessionOnBoardingRepository {
    fun isFirstTimeLaunch(): Boolean
    fun setFirstTimeLaunch()
}