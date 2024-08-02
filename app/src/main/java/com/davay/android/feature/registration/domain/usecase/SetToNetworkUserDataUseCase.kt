package com.davay.android.feature.registration.domain.usecase

import com.davay.android.core.data.dto.UserDto
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.feature.registration.domain.api.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetToNetworkUserDataUseCase @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {
    fun setUserData(userData: UserDto): Flow<Result<UserDto, ErrorType>> {
        return registrationRepository.setUserData(userData)
    }
}