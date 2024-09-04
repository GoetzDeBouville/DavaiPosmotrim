package com.davay.android.core.data.impl

import com.davay.android.BuildConfig
import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.dto.SessionResultDto
import com.davay.android.core.data.network.WebsocketNetworkClient
import com.davay.android.core.domain.api.SessionResultWebsocketRepository
import com.davay.android.core.domain.models.SessionWithMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionResultWebsocketRepositoryImpl @Inject constructor(
    private val websocketSessionResultClient: WebsocketNetworkClient<SessionResultDto?, String>
) : SessionResultWebsocketRepository {

    override fun subscribe(deviceId: String, path: String): Flow<SessionWithMovies?> {
        return websocketSessionResultClient.subscribe(deviceId, path).map { it?.toDomain() }
    }

    override suspend fun unsubscribe() {
        runCatching {
            websocketSessionResultClient.close()
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                error.printStackTrace()
            }
        }
    }
}