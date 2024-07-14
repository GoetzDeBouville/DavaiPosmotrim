package com.davay.android.data.impl

import android.content.SharedPreferences
import com.davay.android.domain.repositories.GetUserIdRepository

class GetUserIdRepositoryImpl(
    private val storage: SharedPreferences
) : GetUserIdRepository {
    override fun getUserId(): String {
        var value = String()
        storage.getString("userId", String())?.let { value = it }
        return value
    }
}