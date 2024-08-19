package com.davay.android.core.data.impl

import com.davay.android.core.data.dto.UserDto
import com.davay.android.core.data.network.HttpNetworkClient
import com.davay.android.core.data.network.model.Response
import com.davay.android.core.data.network.model.UserDataRequest
import com.davay.android.core.data.network.model.mapToErrorType
import com.davay.android.core.domain.api.NetworkUserDataRepository
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID.randomUUID

class NetworkUserDataRepositoryImpl(
    private val httpNetworkClient: HttpNetworkClient<UserDataRequest, HttpResponse>,
    private val userDataRepository: UserDataRepository
) : NetworkUserDataRepository {

    override fun setUserData(userName: String): Flow<Result<Unit, ErrorType>> = flow {
        val response: Response<HttpResponse>
        var userId = userDataRepository.getUserId()
        val isRegistration = userId.isEmpty()
        when (userId) {
            "" -> {
                userId = randomUUID().toString()
                response = httpNetworkClient.getResponse(
                    UserDataRequest.Registration(
                        userData = UserDto(
                            userId = userId, name = userName
                        )
                    )
                )
            }

            else -> {
                response = httpNetworkClient.getResponse(
                    UserDataRequest.ChangeName(
                        userData = UserDto(
                            name = userName, userId = userId
                        )
                    )
                )
            }
        }
        if (response.isSuccess) {
            userDataRepository.setUserName(userName)
            if (isRegistration) userDataRepository.setUserId(userId)
            emit(Result.Success(Unit))
        } else {
            emit(Result.Error(response.resultCode.mapToErrorType()))
        }
    }
}