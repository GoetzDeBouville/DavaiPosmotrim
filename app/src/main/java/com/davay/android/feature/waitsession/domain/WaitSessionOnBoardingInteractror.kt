package com.davay.android.feature.waitsession.domain

interface WaitSessionOnBoardingInteractror {
    fun markFirstTimeLaunch()
    fun isFirstTimeLaunch(): Boolean
}