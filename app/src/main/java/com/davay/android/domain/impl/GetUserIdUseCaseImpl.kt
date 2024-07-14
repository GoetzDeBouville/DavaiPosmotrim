package com.davay.android.domain.impl

import com.davay.android.domain.repositories.GetUserIdRepository
import com.davay.android.domain.usecases.GetSingleDataUseCase

class GetUserIdUseCaseImpl(
    private val repository: GetUserIdRepository
) : GetSingleDataUseCase<String> {
    override fun getSingleData(): String {
        return repository.getUserId()
    }
}