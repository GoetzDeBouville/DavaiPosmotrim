package com.davay.android.data.impl

import android.content.SharedPreferences
import com.davay.android.domain.repositories.GetUserNameRepository

class GetUserNameRepositoryImpl(
    private val storage: SharedPreferences
) : GetUserNameRepository {
    override fun getUserName(): String {
        return storage.getString(USER_NAME, "") ?: ""
    }
    companion object {
        const val USER_NAME = "userName"
    }
}