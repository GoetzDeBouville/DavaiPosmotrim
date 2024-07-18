package com.davay.android.data.impl

import android.content.SharedPreferences
import com.davay.android.domain.repositories.GetUserIdRepository

class GetUserIdRepositoryImpl(
    private val storage: SharedPreferences
) : GetUserIdRepository {
    override fun getUserId(): String {
        return storage.getString(USER_ID, "") ?: ""
    }
    companion object {
        const val USER_ID = "userId"
    }
}