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
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.core.domain.models.SessionWithMovies
import com.davay.android.core.domain.models.User
import com.davay.android.di.MatchesIdClient
import com.davay.android.di.RouletteIdClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun subscribeUsers(sessionId: String): Flow<List<User>?> {
        return websocketUsersClient.subscribe(deviceId, "$sessionId$PATH_USERS")
            .map { it?.map { it.toDomain() } }
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

    override fun subscribeSessionResult(sessionId: String): Flow<SessionWithMovies?> {
        return websocketSessionResultClient.subscribe(deviceId, "$sessionId$PATH_SESSION_RESULT")
            .map { it?.toDomain() }
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

    override fun subscribeSessionStatus(sessionId: String): Flow<SessionStatus?> {
        return websocketSessionStatusClient.subscribe(deviceId, "$sessionId$PATH_SESSION_STATUS")
            .map { it?.toDomain() }
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

    override fun subscribeRouletteId(sessionId: String): Flow<Int?> {
        return websocketRouletteIdClient.subscribe(deviceId, "$sessionId$PATH_ROULETTE")
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

    override fun subscribeMatchesId(sessionId: String): Flow<Int?> {
        return websocketMatchesIdClient.subscribe(deviceId, "$sessionId$PATH_MATCHES")
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