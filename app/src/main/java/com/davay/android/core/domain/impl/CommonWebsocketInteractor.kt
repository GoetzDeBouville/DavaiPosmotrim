package com.davay.android.core.domain.impl

import com.davay.android.core.domain.api.WebsocketRepository
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.core.domain.models.SessionWithMovies
import com.davay.android.core.domain.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonWebsocketInteractor @Inject constructor(
    private val websocketRepository: WebsocketRepository,
) {
    fun subscribeSessionStatus(sessionId: String): Flow<Result<SessionStatus, ErrorType>> {
        return websocketRepository.subscribeSessionStatus(sessionId)
    }

    suspend fun unsubscribeSessionStatus() {
        websocketRepository.unsubscribeSessionStatus()
    }

    fun subscribeUsers(sessionId: String): Flow<Result<List<User>, ErrorType>> {
        return websocketRepository.subscribeUsers(sessionId)
    }

    suspend fun unsubscribeUsers() {
        websocketRepository.unsubscribeUsers()
    }

    fun subscribeSessionResult(sessionId: String): Flow<Result<SessionWithMovies, ErrorType>> {
        return websocketRepository.subscribeSessionResult(sessionId)
    }

    suspend fun unsubscribeSessionResult() {
        websocketRepository.unsubscribeSessionResult()
    }

    fun subscribeRouletteId(sessionId: String): Flow<Result<Int, ErrorType>> {
        return websocketRepository.subscribeRouletteId(sessionId)
    }

    suspend fun unsubscribeRouletteId() {
        websocketRepository.unsubscribeRouletteId()
    }

    fun subscribeMatchesId(sessionId: String): Flow<Result<Int, ErrorType>> {
        return websocketRepository.subscribeMatchesId(sessionId)
    }

    suspend fun unsubscribeMatchesId() {
        websocketRepository.unsubscribeMatchesId()
    }
}