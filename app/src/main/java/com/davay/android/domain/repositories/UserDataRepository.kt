package com.davay.android.domain.repositories

interface UserDataRepository {
    fun setUserName(userName: String)
    fun setUserId()
    fun getUserName(): String
    fun getUserId(): String
}