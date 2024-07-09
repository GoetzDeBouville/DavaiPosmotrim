package com.davay.android.userData

interface UserDataInteractor {
    fun getUserId(): String
    fun getUserName(): String
    fun setUserId(userId: String)
    fun setUserName(userName: String)
}