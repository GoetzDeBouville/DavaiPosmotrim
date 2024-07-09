package com.davay.android.feature.registration.data

import android.content.SharedPreferences
import com.davay.android.feature.registration.domain.UserDataRepository

class UserDataRepositoryImpl(
    private val storage: SharedPreferences
) : UserDataRepository {
    override fun getUserId(): String {
        var value = String()
        storage.getString(USER_ID, String())?.let { value = it }
        return value
    }

    override fun getUserName(): String {
        var value = String()
        storage.getString(USER_NAME, String())?.let { value = it }
        return value
    }

    override fun setUserId(userId: String) {
        storage
            .edit()
            .putString(USER_ID, userId)
            .apply()
    }

    override fun setUserName(userName: String) {
        storage
            .edit()
            .putString(USER_NAME, userName)
            .apply()
    }

    companion object {
        const val USER_ID = "userId"
        const val USER_NAME = "userName"
    }
}
