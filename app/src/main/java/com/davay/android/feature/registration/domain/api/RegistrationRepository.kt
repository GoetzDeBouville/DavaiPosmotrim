package com.davay.android.feature.registration.domain.api

import com.davay.android.core.data.dto.UserDto
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    fun setUserData(userData: UserDto): Flow<Result<UserDto, ErrorType>>
}