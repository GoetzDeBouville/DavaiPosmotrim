package com.davay.android.data.repositories

import android.content.SharedPreferences
import com.davay.android.domain.usecases.GetDataByKeyUseCase

class GetSPUserRepository(
    private val storage: SharedPreferences
) : GetDataByKeyUseCase<String> {
    override fun getSharedPreferences(key: String): String {
        var value = String()
        storage.getString(key, String())?.let { value = it }
        return value
    }
}