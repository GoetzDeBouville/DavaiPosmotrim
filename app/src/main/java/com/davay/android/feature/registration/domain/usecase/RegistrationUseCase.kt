package com.davay.android.feature.registration.domain.usecase

import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.core.domain.models.User
import com.davay.android.feature.registration.domain.api.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {
    fun setUserData(userName: String): Flow<Result<User, ErrorType>> {
        return registrationRepository.setUserData(userName)
    }
}