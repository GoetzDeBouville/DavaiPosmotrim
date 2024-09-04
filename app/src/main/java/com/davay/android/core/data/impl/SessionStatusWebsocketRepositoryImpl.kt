package com.davay.android.core.data.impl

import com.davay.android.BuildConfig
import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.converters.toDto
import com.davay.android.core.data.dto.SessionStatusDto
import com.davay.android.core.data.network.WebsocketNetworkClient
import com.davay.android.core.domain.api.SessionStatusWebsocketRepository
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.di.CommonWebsocketModule.Companion.SESSION_STATUS_CLIENT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class SessionStatusWebsocketRepositoryImpl @Inject constructor(
    @Named(SESSION_STATUS_CLIENT)
    private val websocketSessionStatusClient: WebsocketNetworkClient<SessionStatusDto, SessionStatusDto>
) : SessionStatusWebsocketRepository {

    override fun subscribe(deviceId: String, path: String): Flow<SessionStatus> {
        return websocketSessionStatusClient.subscribe(deviceId, path).map { it.toDomain() }
    }

    override suspend fun sendMessage(message: SessionStatus) {
        runCatching {
            websocketSessionStatusClient.sendMessage(message = message.toDto())
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                error.printStackTrace()
            }
        }
    }

    override suspend fun unsubscribe() {
        runCatching {
            websocketSessionStatusClient.close()
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                error.printStackTrace()
            }
        }
    }
}