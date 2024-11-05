package com.davay.android.core.domain.impl

import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.models.UserDataFields
import com.davay.android.core.domain.usecases.GetUserDataUseCase

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