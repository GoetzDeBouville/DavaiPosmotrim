package com.davay.android.core.domain.api

import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.core.domain.models.SessionWithMovies
import com.davay.android.core.domain.models.User
import kotlinx.coroutines.flow.StateFlow

interface WebsocketRepository {
    fun subscribeUsers(sessionId: String): StateFlow<Result<List<User>, ErrorType>?>
    suspend fun unsubscribeUsers()
    fun subscribeSessionResult(sessionId: String): StateFlow<Result<SessionWithMovies, ErrorType>?>
    suspend fun unsubscribeSessionResult()
    fun subscribeSessionStatus(sessionId: String): StateFlow<Result<SessionStatus, ErrorType>?>
    suspend fun unsubscribeSessionStatus()
    fun subscribeRouletteId(sessionId: String): StateFlow<Result<Int, ErrorType>?>
    suspend fun unsubscribeRouletteId()
    fun subscribeMatchesId(sessionId: String): StateFlow<Result<Int, ErrorType>?>
    suspend fun unsubscribeMatchesId()
}