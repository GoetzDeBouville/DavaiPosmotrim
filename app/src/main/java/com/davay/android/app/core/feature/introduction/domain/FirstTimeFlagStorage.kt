package com.davay.android.app.core.feature.introduction.domain

interface FirstTimeFlagStorage {

    fun isFirstTimeLaunch(): Boolean
    fun markFirstTimeLaunch()

    companion object {
        const val STORAGE_NAME = "introductionSettings"
    }
}