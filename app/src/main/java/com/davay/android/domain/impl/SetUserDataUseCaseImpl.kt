package com.davay.android.domain.impl

import com.davay.android.domain.models.UserDataFields
import com.davay.android.domain.repositories.UserDataRepository
import com.davay.android.domain.usecases.SetUserDataUseCase

class SetUserDataUseCaseImpl(
    private val repository: UserDataRepository
) : SetUserDataUseCase {

    override fun setUserData(value: UserDataFields) {
        when (value) {
            is UserDataFields.UserName -> repository.setUserName(value.userName)
            is UserDataFields.UserId -> repository.setUserId()
        }
    }
}