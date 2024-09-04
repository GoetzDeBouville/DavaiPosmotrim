package com.davay.android.core.domain.api

import com.davay.android.core.domain.models.SessionWithMovies
import kotlinx.coroutines.flow.Flow

interface SessionResultWebsocketRepository {
    fun subscribe(deviceId: String, path: String): Flow<SessionWithMovies?>
    suspend fun unsubscribe()
}