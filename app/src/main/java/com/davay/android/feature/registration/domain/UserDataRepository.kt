package com.davay.android.feature.registration.domain

interface UserDataRepository {
    fun getUserId(): String
    fun getUserName(): String
    fun setUserId(userId: String)
    fun setUserName(userName: String)
}