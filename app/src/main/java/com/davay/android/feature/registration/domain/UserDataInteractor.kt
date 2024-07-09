package com.davay.android.feature.registration.domain

interface UserDataInteractor {
    fun getUserId(): String
    fun getUserName(): String
    fun setUserId(userId: String)
    fun setUserName(userName: String)
}