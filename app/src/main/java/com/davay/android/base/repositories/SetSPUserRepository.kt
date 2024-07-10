package com.davay.android.base.repositories

import android.content.SharedPreferences
import com.davay.android.base.usecases.SetSharedPreferences

class SetSPUserRepository(
    private val userStorage: SharedPreferences
) : SetSharedPreferences<String> {
    override fun setSharedPreferences(key: String, value: String) {
        userStorage
            .edit()
            .putString(key, value)
            .apply()
    }
}