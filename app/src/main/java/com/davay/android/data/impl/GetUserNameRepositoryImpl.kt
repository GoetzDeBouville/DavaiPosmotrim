package com.davay.android.data.impl

import android.content.SharedPreferences
import com.davay.android.domain.repositories.GetUserNameRepository

class GetUserNameRepositoryImpl(
    private val storage: SharedPreferences
) : GetUserNameRepository {
    override fun getUserName(): String {
        var value = String()
        storage.getString(USER_NAME, String())?.let { value = it }
        return value
    }
    companion object {
        const val USER_NAME = "userName"
    }
}