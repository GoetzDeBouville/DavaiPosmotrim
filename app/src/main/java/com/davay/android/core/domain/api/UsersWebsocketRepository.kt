package com.davay.android.core.domain.api

import com.davay.android.core.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UsersWebsocketRepository {
    fun subscribe(deviceId: String, path: String): Flow<List<User>>
    suspend fun unsubscribe()
}