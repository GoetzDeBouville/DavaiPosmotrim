package com.davay.android.core.data.impl

import com.davay.android.BuildConfig
import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.dto.UserDto
import com.davay.android.core.data.network.WebsocketNetworkClient
import com.davay.android.core.domain.api.UsersWebsocketRepository
import com.davay.android.core.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UsersWebsocketRepositoryImpl @Inject constructor(
    private val websocketUsersClient: WebsocketNetworkClient<List<UserDto>, String>
) : UsersWebsocketRepository {

    override fun subscribe(deviceId: String, path: String): Flow<List<User>> {
        return websocketUsersClient.subscribe(deviceId, path).map { it.map { it.toDomain() } }
    }

    override suspend fun unsubscribe() {
        runCatching {
            websocketUsersClient.close()
        }.onFailure { error ->
            if (BuildConfig.DEBUG) {
                error.printStackTrace()
            }
        }
    }
}