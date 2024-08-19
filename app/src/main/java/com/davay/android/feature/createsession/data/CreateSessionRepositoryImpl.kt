package com.davay.android.feature.createsession.data

import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.network.HttpNetworkClient
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.models.CompilationFilms
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Genre
import com.davay.android.core.domain.models.Result
import com.davay.android.core.domain.models.Session
import com.davay.android.feature.createsession.data.network.CreateSessionRequest
import com.davay.android.feature.createsession.data.network.CreateSessionResponse
import com.davay.android.feature.createsession.domain.api.CreateSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateSessionRepositoryImpl @Inject constructor(
    private val httpNetworkClient: HttpNetworkClient<CreateSessionRequest, CreateSessionResponse>,
    private val userDataRepository: UserDataRepository
) : CreateSessionRepository {
    override fun getCollections(): Flow<Result<List<CompilationFilms>, ErrorType>> = flow {
        val response = httpNetworkClient.getResponse(CreateSessionRequest.CollectionList)
        when (val body = response.body) {
            is CreateSessionResponse.CollectionList -> {
                emit(Result.Success(body.value.map { it.toDomain() }))
            }

            else -> {
                emit(Result.Error(response.resultCode.mapToErrorType()))
            }
        }
    }

    override fun getGenres(): Flow<Result<List<Genre>, ErrorType>> = flow {
        val response = httpNetworkClient.getResponse(CreateSessionRequest.GenreList)
        when (val body = response.body) {
            is CreateSessionResponse.GenreList -> {
                emit(Result.Success(body.value.map { it.toDomain() }))
            }

            else -> {
                emit(Result.Error(response.resultCode.mapToErrorType()))
            }
        }
    }

    override fun createSession(
        parameter: String,
        requestBody: List<String>
    ): Flow<Result<Session, ErrorType>> = flow {
        val userId = userDataRepository.getUserId()
        val response = httpNetworkClient.getResponse(
            CreateSessionRequest.Session(
                parameter,
                requestBody,
                userId
            )
        )
        when (val body = response.body) {
            is CreateSessionResponse.Session -> {
                emit(Result.Success(body.value.toDomain()))
            }

            else -> {
                emit(Result.Error(response.resultCode.mapToErrorType()))
            }
        }
    }
}