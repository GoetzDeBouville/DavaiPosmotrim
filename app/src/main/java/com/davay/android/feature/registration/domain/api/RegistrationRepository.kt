package com.davay.android.feature.registration.domain.api

import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.core.domain.models.User
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    fun setUserData(userName: String): Flow<Result<User, ErrorType>>
}