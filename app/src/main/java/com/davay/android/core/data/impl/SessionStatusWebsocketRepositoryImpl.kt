package com.davay.android.core.data.impl

import com.davay.android.BuildConfig
import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.dto.SessionStatusDto
import com.davay.android.core.data.network.WebsocketNetworkClient
import com.davay.android.core.domain.api.SessionStatusWebsocketRepository
import com.davay.android.core.domain.models.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionStatusWebsocketRepositoryImpl @Inject constructor(
    private val websocketSessionStatusClient: WebsocketNetworkClient<SessionStatusDto, String>
) : SessionStatusWebsocketRepository {

    override fun subscribe(deviceId: String, path: String): Flow<SessionStatus> {
        return websocketSessionStatusClient.subscribe(deviceId, path).map { it.toDomain() }
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