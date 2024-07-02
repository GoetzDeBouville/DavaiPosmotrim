package com.davay.android.feature.waitsession.domain

interface FirstTimeFlagForWaitSessionStorage {
    fun isFirstTimeLaunch(): Boolean
    fun setFirstTimeLaunch()
}