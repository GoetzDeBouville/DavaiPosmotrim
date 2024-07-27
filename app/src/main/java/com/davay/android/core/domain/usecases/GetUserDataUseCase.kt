package com.davay.android.core.domain.usecases

import com.davay.android.core.domain.models.UserDataFields

interface GetUserDataUseCase {
    fun getUserData(userData: UserDataFields): String
}