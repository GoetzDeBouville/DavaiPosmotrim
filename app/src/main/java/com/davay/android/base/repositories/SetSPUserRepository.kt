package com.davay.android.base.repositories

import android.content.SharedPreferences
import com.davay.android.base.usecases.SetDataByKeyUseCase

class SetSPUserRepository(
    private val userStorage: SharedPreferences
) : SetDataByKeyUseCase<String> {
    override fun setSharedPreferences(key: String, value: String) {
        userStorage
            .edit()
            .putString(key, value)
            .apply()
    }
}