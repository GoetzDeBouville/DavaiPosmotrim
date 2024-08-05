package com.davay.android.feature.registration.data

import com.davay.android.core.data.dto.UserDto
import com.davay.android.core.data.network.HttpNetworkClient
import com.davay.android.core.data.network.model.mapToErrorType
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.feature.registration.data.network.RegistrationRequest
import com.davay.android.feature.registration.data.network.RegistrationResponse
import com.davay.android.feature.registration.domain.api.RegistrationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID.randomUUID

class RegistrationRepositoryImpl(
    private val httpNetworkClient: HttpNetworkClient<RegistrationRequest, RegistrationResponse>,
    private val userDataRepository: UserDataRepository
) : RegistrationRepository {

    override fun setUserData(userName: String): Flow<Result<UserDto, ErrorType>> = flow {
        val userId = randomUUID().toString()
        val response = httpNetworkClient.getResponse(
            RegistrationRequest(
                userData = UserDto(
                    userId = userId,
                    name = userName
                )
            )
        )
        when (val body = response.body) {
            is RegistrationResponse -> {
                with (userDataRepository) {
                    setUserId(body.userData.userId)
                    setUserName(body.userData.name)
                }
                emit(Result.Success(body.userData))
            }
            else -> {
                emit(Result.Error(response.resultCode.mapToErrorType()))
            }
        }
    }
}