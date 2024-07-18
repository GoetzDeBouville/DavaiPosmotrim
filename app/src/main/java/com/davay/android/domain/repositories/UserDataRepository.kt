package com.davay.android.domain.repositories

interface UserDataRepository {
    fun setUserName(value: String)
    fun setUserId(value: String)
    fun getUserName(): String
    fun getUserId(): String
}