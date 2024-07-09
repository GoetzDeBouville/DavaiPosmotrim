package com.davay.android.userdata

interface UserDataRepository {
    fun getUserId(): String
    fun getUserName(): String
    fun setUserId(userId: String)
    fun setUserName(userName: String)
}