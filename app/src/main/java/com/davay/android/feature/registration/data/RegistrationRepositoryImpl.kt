package com.davay.android.feature.registration.data

import com.davay.android.core.data.dto.UserDto
import com.davay.android.core.data.network.HttpNetworkClient
import com.davay.android.core.data.network.model.mapToErrorType
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.feature.registration.data.network.RegistrationRequest
import com.davay.android.feature.registration.data.network.RegistrationResponse
import com.davay.android.feature.registration.domain.api.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegistrationRepositoryImpl(
    private val httpNetworkClient: HttpNetworkClient<RegistrationRequest, RegistrationResponse>
) : RegistrationRepository {

    override fun setUserData(userData: UserDto): Flow<Result<UserDto, ErrorType>> = flow {
        val response = httpNetworkClient.getResponse(RegistrationRequest(userData = userData))
        when (val body = response.body) {
            is RegistrationResponse -> {
                emit(Result.Success(body.userData))
            }
            else -> {
                emit(Result.Error(response.resultCode.mapToErrorType()))
            }
        }
    }
}