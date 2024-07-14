package com.davay.android.domain.impl

import com.davay.android.domain.repositories.SetUserIdRepository
import com.davay.android.domain.usecases.SetSingleDataUseCase

class SetUserIdUseCaseImpl(
    private val repository: SetUserIdRepository
) : SetSingleDataUseCase<String> {
    override fun setSingleData(value: String) {
        repository.setUserId(value)
    }
}