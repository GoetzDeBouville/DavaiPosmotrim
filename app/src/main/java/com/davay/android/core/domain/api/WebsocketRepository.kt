package com.davay.android.core.domain.api

import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.core.domain.models.SessionWithMovies
import com.davay.android.core.domain.models.User
import kotlinx.coroutines.flow.Flow

interface WebsocketRepository {
    fun subscribeUsers(sessionId: String): Flow<List<User>?>
    suspend fun unsubscribeUsers()
    fun subscribeSessionResult(sessionId: String): Flow<SessionWithMovies?>
    suspend fun unsubscribeSessionResult()
    fun subscribeSessionStatus(sessionId: String): Flow<SessionStatus?>
    suspend fun unsubscribeSessionStatus()
    fun subscribeRouletteId(sessionId: String): Flow<Int?>
    suspend fun unsubscribeRouletteId()
    fun subscribeMatchesId(sessionId: String): Flow<Int?>
    suspend fun unsubscribeMatchesId()
}