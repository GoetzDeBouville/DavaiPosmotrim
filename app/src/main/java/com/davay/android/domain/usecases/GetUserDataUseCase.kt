package com.davay.android.domain.usecases

import com.davay.android.domain.models.UserDataFields

interface GetUserDataUseCase {
    fun getUserData(userData: UserDataFields): String
}