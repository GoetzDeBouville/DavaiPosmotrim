package com.davay.android.data.repositories

import android.content.SharedPreferences
import com.davay.android.domain.usecases.SetDataByKeyUseCase

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