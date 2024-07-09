package com.davay.android.userdata

interface UserDataInteractor {
    fun getUserId(): String
    fun getUserName(): String
    fun setUserId(userId: String)
    fun setUserName(userName: String)
}