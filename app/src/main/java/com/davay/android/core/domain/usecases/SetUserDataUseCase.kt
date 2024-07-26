package com.davay.android.core.domain.usecases

import com.davay.android.core.domain.models.UserDataFields

interface SetUserDataUseCase {
    fun setUserData(value: UserDataFields)
}