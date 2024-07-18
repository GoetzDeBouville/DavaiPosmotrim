package com.davay.android.domain.usecases

import com.davay.android.domain.models.UserDataFields

interface GetUserData {
    fun <T>getUserData(userData: UserDataFields): T
}