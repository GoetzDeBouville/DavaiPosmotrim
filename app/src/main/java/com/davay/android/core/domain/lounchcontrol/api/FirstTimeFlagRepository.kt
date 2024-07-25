package com.davay.android.core.domain.lounchcontrol.api

interface FirstTimeFlagRepository {
    fun isFirstTimeLaunch(): Boolean
    fun markFirstTimeLaunch()
}