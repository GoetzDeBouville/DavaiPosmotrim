package com.davay.android.core.domain.api

import com.davay.android.core.domain.models.SessionStatus
import kotlinx.coroutines.flow.Flow

interface SessionStatusWebsocketRepository {
    fun subscribe(deviceId: String, path: String): Flow<SessionStatus>
    suspend fun unsubscribe()
}