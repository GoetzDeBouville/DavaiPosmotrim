package com.davay.android.domain.impl

import com.davay.android.domain.models.UserDataFields
import com.davay.android.domain.repositories.UserDataRepository
import com.davay.android.domain.usecases.GetUserDataUseCase

class GetUserDataUseCaseImpl(
    private val repository: UserDataRepository
) : GetUserDataUseCase {
    override fun getUserData(userData: UserDataFields): String {
        return when (userData) {
            is UserDataFields.UserName -> repository.getUserName()
            is UserDataFields.UserId -> repository.getUserId()
        }
    }
}