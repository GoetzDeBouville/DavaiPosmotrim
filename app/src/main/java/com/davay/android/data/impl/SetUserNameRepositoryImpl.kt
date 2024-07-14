package com.davay.android.data.impl

import android.content.SharedPreferences
import com.davay.android.domain.repositories.SetUserNameRepository

class SetUserNameRepositoryImpl(
    private val userStorage: SharedPreferences
) : SetUserNameRepository {
    override fun setUserName(value: String) {
        userStorage
            .edit()
            .putString(USER_NAME, value)
            .apply()
    }
    companion object {
        const val USER_NAME = "userName"
    }
}