package com.davay.android.core.domain.repositories

interface UserDataRepository {
    fun setUserName(userName: String)
    fun setUserId()
    fun getUserName(): String
    fun getUserId(): String
}