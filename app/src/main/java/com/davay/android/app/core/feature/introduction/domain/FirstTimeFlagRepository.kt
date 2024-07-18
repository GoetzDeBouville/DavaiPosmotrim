package com.davay.android.app.core.feature.introduction.domain

interface FirstTimeFlagRepository {
    fun isFirstTimeLaunch(): Boolean
    fun markFirstTimeLaunch()
}