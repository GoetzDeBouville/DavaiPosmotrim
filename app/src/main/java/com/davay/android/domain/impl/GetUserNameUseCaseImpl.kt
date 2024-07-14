package com.davay.android.domain.impl

import com.davay.android.domain.repositories.GetUserNameRepository
import com.davay.android.domain.usecases.GetSingleDataUseCase

class GetUserNameUseCaseImpl(
    private val repository: GetUserNameRepository
) : GetSingleDataUseCase<String> {
    override fun getSingleData(): String {
        return repository.getUserName()
    }
}