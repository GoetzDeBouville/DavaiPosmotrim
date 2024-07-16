package com.davay.android.feature.splash.domain

interface FirstTimeFlagStorage {

    fun isFirstTimeLaunch(): Boolean
    fun markFirstTimeLaunch()

    companion object {
        const val STORAGE_NAME = "introductionSettings"
    }
}