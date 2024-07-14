package com.davay.android.data.impl

import android.content.SharedPreferences
import com.davay.android.domain.repositories.SetUserIdRepository

class SetUserIdRepositoryImpl(
    private val userStorage: SharedPreferences
) : SetUserIdRepository {
    override fun setUserId(value: String) {
        userStorage
            .edit()
            .putString(USER_ID, value)
            .apply()
    }
    companion object {
        const val USER_ID = "userId"
    }
}