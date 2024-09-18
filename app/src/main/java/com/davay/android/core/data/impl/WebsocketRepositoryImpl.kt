package com.davay.android.core.data.impl

import com.davay.android.BuildConfig
import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.dto.SessionResultDto
import com.davay.android.core.data.dto.SessionStatusDto
import com.davay.android.core.data.dto.UserDto
import com.davay.android.core.data.network.WebsocketNetworkClient
import com.davay.android.core.data.network.model.NetworkParams.PATH_MATCHES
import com.davay.android.core.data.network.model.NetworkParams.PATH_ROULETTE
import com.davay.android.core.data.network.model.NetworkParams.PATH_SESSION_RESULT
import com.davay.android.core.data.network.model.NetworkParams.PATH_SESSION_STATUS
import com.davay.android.core.data.network.model.NetworkParams.PATH_USERS
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.api.WebsocketRepository
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.core.domain.models.SessionWithMovies
import com.davay.android.core.domain.models.User
import com.davay.android.di.MatchesIdClient
import com.davay.android.di.RouletteIdClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class WebsocketRepositoryImpl @Inject constructor(
    private val websocketUsersClient: WebsocketNetworkClient<List<UserDto>?>,
    private val websocketSessionResultClient: WebsocketNetworkClient<SessionResultDto?>,
    private val websocketSessionStatusClient: WebsocketNetworkClient<SessionStatusDto?>,
    @RouletteIdClient private val websocketRouletteIdClient: WebsocketNetworkClient<Int?>,
    @MatchesIdClient private val websocketMatchesIdClient: WebsocketNetworkClient<Int?>,
    private val userDataRepository: UserDataRepository,
) : WebsocketRepository {

    private val deviceId = userDataRepository.getUserId()
    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun subscribeUsers(sessionId: String): StateFlow<Result<List<User>, ErrorType>?> =
        subscribeUsersFlow(sessionId).stateIn(
            scope = repositoryScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    private fun subscribeUsersFlow(sessionId: String): Flow<Result<List<User>, ErrorType>> = flow {
        @Suppress("TooGenericExceptionCaught")
        try {
            websocketUsersClient.subscribe(deviceId, "$sessionId$PATH_USERS").collect { list ->
                if (list != null) {
                    emit(Result.Success(list.map { it.toDomain() }))
                } else {
                    emit(Result.Error(ErrorType.UNKNOWN_ERROR))
                }
            }
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
            emit(Result.Error(ErrorType.NO_CONNECTION))
        }
    }

    override suspend fun unsubscribeUsers() {
        runCatching {
            websocketUsersClient.close()
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                error.printStackTrace()
            }
        }
    }

    override fun subscribeSessionResult(sessionId: String): StateFlow<Result<SessionWithMovies, ErrorType>?> =
        subscribeSessionResultFlow(sessionId).stateIn(
            scope = repositoryScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    private fun subscribeSessionResultFlow(sessionId: String): Flow<Result<SessionWithMovies, ErrorType>?> =
        flow {
            @Suppress("TooGenericExceptionCaught")
            try {
                websocketSessionResultClient.subscribe(deviceId, "$sessionId$PATH_SESSION_RESULT")
                    .collect { sessionResult ->
                        if (sessionResult != null) {
                            emit(Result.Success(sessionResult.toDomain()))
                        } else {
                            emit(Result.Error(ErrorType.UNKNOWN_ERROR))
                        }
                    }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace()
                }
                emit(Result.Error(ErrorType.NO_CONNECTION))
            }
        }

    override suspend fun unsubscribeSessionResult() {
        runCatching {
            websocketSessionResultClient.close()
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                error.printStackTrace()
            }
        }
    }

    override fun subscribeSessionStatus(sessionId: String): StateFlow<Result<SessionStatus, ErrorType>?> =
        subscribeSessionStatusFlow(sessionId).stateIn(
            scope = repositoryScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    private fun subscribeSessionStatusFlow(sessionId: String): Flow<Result<SessionStatus, ErrorType>?> =
        flow {
            @Suppress("TooGenericExceptionCaught")
            try {
                websocketSessionStatusClient.subscribe(deviceId, "$sessionId$PATH_SESSION_STATUS")
                    .collect { sessionStatus ->
                        if (sessionStatus != null) {
                            emit(Result.Success(sessionStatus.toDomain()))
                        } else {
                            emit(Result.Error(ErrorType.UNKNOWN_ERROR))
                        }
                    }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace()
                }
                emit(Result.Error(ErrorType.NO_CONNECTION))
            }
        }

    override suspend fun unsubscribeSessionStatus() {
        runCatching {
            websocketSessionStatusClient.close()
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                error.printStackTrace()
            }
        }
    }

    override fun subscribeRouletteId(sessionId: String): StateFlow<Result<Int, ErrorType>?> =
        subscribeRouletteIdFlow(sessionId).stateIn(
            scope = repositoryScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    private fun subscribeRouletteIdFlow(sessionId: String): Flow<Result<Int, ErrorType>?> =
        flow {
            @Suppress("TooGenericExceptionCaught")
            try {
                websocketRouletteIdClient.subscribe(deviceId, "$sessionId$PATH_ROULETTE")
                    .collect { id ->
                        if (id != null) {
                            emit(Result.Success(id))
                        } else {
                            emit(Result.Error(ErrorType.UNKNOWN_ERROR))
                        }
                    }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace()
                }
                emit(Result.Error(ErrorType.NO_CONNECTION))
            }
        }

    override suspend fun unsubscribeRouletteId() {
        runCatching {
            websocketRouletteIdClient.close()
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                error.printStackTrace()
            }
        }
    }

    override fun subscribeMatchesId(sessionId: String): StateFlow<Result<Int, ErrorType>?> =
        subscribeMatchesIdFlow(sessionId).stateIn(
            scope = repositoryScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    private fun subscribeMatchesIdFlow(sessionId: String): Flow<Result<Int, ErrorType>?> =
        flow {
            @Suppress("TooGenericExceptionCaught")
            try {
                websocketMatchesIdClient.subscribe(deviceId, "$sessionId$PATH_MATCHES")
                    .collect { id ->
                        if (id != null) {
                            emit(Result.Success(id))
                        } else {
                            emit(Result.Error(ErrorType.UNKNOWN_ERROR))
                        }
                    }
            } catch (e: Exception) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace()
                }
                emit(Result.Error(ErrorType.NO_CONNECTION))
            }
        }

    override suspend fun unsubscribeMatchesId() {
        runCatching {
            websocketMatchesIdClient.close()
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                error.printStackTrace()
            }
        }
    }
}