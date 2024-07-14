package com.davay.android.data.impl

import android.content.SharedPreferences
import com.davay.android.data.repositories.SetUserIdRepository

class SetUserIdRepositoryImpl(
    private val userStorage: SharedPreferences
) : SetUserIdRepository {
    override fun setUserId(userId: String) {
        userStorage
            .edit()
            .putString("userId", userId)
            .apply()
    }
}