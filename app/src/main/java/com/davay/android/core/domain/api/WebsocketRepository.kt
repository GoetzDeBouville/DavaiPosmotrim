package com.davay.android.core.domain.api

import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.core.domain.models.SessionWithMovies
import com.davay.android.core.domain.models.User
import kotlinx.coroutines.flow.Flow

interface WebsocketRepository {
    fun subscribeUsers(deviceId: String, path: String): Flow<List<User>>
    suspend fun unsubscribeUsers()
    fun subscribeSessionResult(deviceId: String, path: String): Flow<SessionWithMovies?>
    suspend fun unsubscribeSessionResult()
    fun subscribeSessionStatus(deviceId: String, path: String): Flow<SessionStatus>
    suspend fun unsubscribeSessionStatus()
}