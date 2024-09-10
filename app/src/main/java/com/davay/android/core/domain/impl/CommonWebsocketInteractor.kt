package com.davay.android.core.domain.impl

import com.davay.android.core.domain.api.WebsocketRepository
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.core.domain.models.SessionWithMovies
import com.davay.android.core.domain.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommonWebsocketInteractor @Inject constructor(
    private val websocketRepository: WebsocketRepository,
) {
    fun subscribeSessionStatus(sessionId: String): Flow<SessionStatus?> {
        return websocketRepository.subscribeSessionStatus(sessionId)
    }

    suspend fun unsubscribeSessionStatus() {
        websocketRepository.unsubscribeSessionStatus()
    }

    fun subscribeUsers(sessionId: String): Flow<List<User>?> {
        return websocketRepository.subscribeUsers(sessionId)
    }

    suspend fun unsubscribeUsers() {
        websocketRepository.unsubscribeUsers()
    }

    fun subscribeSessionResult(sessionId: String): Flow<SessionWithMovies?> {
        return websocketRepository.subscribeSessionResult(sessionId)
    }

    suspend fun unsubscribeSessionResult() {
        websocketRepository.unsubscribeSessionResult()
    }

    fun subscribeRouletteId(sessionId: String): Flow<Int?> {
        return websocketRepository.subscribeRouletteId(sessionId)
    }

    suspend fun unsubscribeRouletteId() {
        websocketRepository.unsubscribeRouletteId()
    }

    fun subscribeMatchesId(sessionId: String): Flow<Int?> {
        return websocketRepository.subscribeMatchesId(sessionId)
    }

    suspend fun unsubscribeMatchesId() {
        websocketRepository.unsubscribeMatchesId()
    }
}