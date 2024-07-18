package com.davay.android.domain.usecases

import com.davay.android.domain.models.UserDataFields

interface SetUserDataUseCase {
    fun setUserData(value: UserDataFields)
}