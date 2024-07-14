package com.davay.android.domain.impl

import com.davay.android.domain.repositories.SetUserNameRepository
import com.davay.android.domain.usecases.SetSingleDataUseCase

class SetUserNameUseCaseImpl(
    private val repository: SetUserNameRepository
) : SetSingleDataUseCase<String> {
    override fun setSingleData(value: String) {
        repository.setUserName(value)
    }
}