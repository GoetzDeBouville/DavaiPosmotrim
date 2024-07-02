package com.davay.android.feature.waitsession.domain

interface WaitSessionOnBoardingInteractror {
    fun setFirstTimeLaunch()
    fun isFirstTimeLaunch(): Boolean
}