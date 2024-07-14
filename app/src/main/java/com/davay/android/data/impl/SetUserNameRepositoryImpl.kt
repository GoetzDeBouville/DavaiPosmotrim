package com.davay.android.data.impl

import android.content.SharedPreferences
import com.davay.android.data.repositories.SetUserNameRepository

class SetUserNameRepositoryImpl(
    private val userStorage: SharedPreferences
) : SetUserNameRepository {
    override fun setUserName(userName: String) {
        userStorage
            .edit()
            .putString("userName", userName)
            .apply()
    }
}