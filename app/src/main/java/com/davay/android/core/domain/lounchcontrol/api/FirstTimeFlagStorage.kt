package com.davay.android.core.domain.lounchcontrol.api

interface FirstTimeFlagStorage {

    fun isFirstTimeLaunch(): Boolean
    fun markFirstTimeLaunch()

    companion object {
        const val STORAGE_NAME = "introductionSettings"
    }
}