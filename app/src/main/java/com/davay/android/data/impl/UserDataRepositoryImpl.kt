package com.davay.android.data.impl

import android.content.SharedPreferences
import com.davay.android.domain.repositories.UserDataRepository
import java.util.UUID.randomUUID

class UserDataRepositoryImpl(
    private val userDataStorage: SharedPreferences
) : UserDataRepository {
    override fun setUserName(userName: String) {
        userDataStorage
            .edit()
            .putString(USER_NAME, userName)
            .apply()
    }

    override fun setUserId() {
        val userId = randomUUID()
            .toString()
            .plus(USER_ID_POSTFIX)
            .plus(System.currentTimeMillis())
        userDataStorage
            .edit()
            .putString(USER_ID, userId)
            .apply()
    }

    override fun getUserName(): String {
        return userDataStorage.getString(USER_NAME, "") ?: ""
    }

    override fun getUserId(): String {
        return userDataStorage.getString(USER_ID, "") ?: ""
    }

    companion object {
        const val USER_ID = "userId"
        const val USER_NAME = "userName"
        private const val USER_ID_POSTFIX = "_android_"
    }
}