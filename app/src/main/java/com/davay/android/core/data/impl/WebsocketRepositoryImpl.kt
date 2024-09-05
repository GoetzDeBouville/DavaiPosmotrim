package com.davay.android.core.data.impl

import com.davay.android.BuildConfig
import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.dto.SessionResultDto
import com.davay.android.core.data.dto.SessionStatusDto
import com.davay.android.core.data.dto.UserDto
import com.davay.android.core.data.network.WebsocketNetworkClient
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
    private val websocketUsersClient: WebsocketNetworkClient<List<UserDto>, String>,
    private val websocketSessionResultClient: WebsocketNetworkClient<SessionResultDto?, String>,
    private val websocketSessionStatusClient: WebsocketNetworkClient<SessionStatusDto, String>,
    @RouletteIdClient private val websocketRouletteIdClient: WebsocketNetworkClient<Int?, String>,
    @MatchesIdClient private val websocketMatchesIdClient: WebsocketNetworkClient<Int?, String>,
) : WebsocketRepository {

    override fun subscribeUsers(deviceId: String, path: String): Flow<List<User>> {
        return websocketUsersClient.subscribe(deviceId, path).map { it.map { it.toDomain() } }
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

    override fun subscribeSessionResult(deviceId: String, path: String): Flow<SessionWithMovies?> {
        return websocketSessionResultClient.subscribe(deviceId, path).map { it?.toDomain() }
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

    override fun subscribeSessionStatus(deviceId: String, path: String): Flow<SessionStatus> {
        return websocketSessionStatusClient.subscribe(deviceId, path).map { it.toDomain() }
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

    override fun subscribeRouletteId(deviceId: String, path: String): Flow<Int?> {
        return websocketRouletteIdClient.subscribe(deviceId, path)
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

    override fun subscribeMatchesId(deviceId: String, path: String): Flow<Int?> {
        return websocketMatchesIdClient.subscribe(deviceId, path)
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