package com.davay.android.core.domain.api

interface UserDataRepository {
    fun setUserName(userName: String)
    fun setUserId()
    fun getUserName(): String
    fun getUserId(): String
}