package com.davay.android.core.domain.impl

import com.davay.android.core.domain.api.SessionStatusWebsocketRepository
import com.davay.android.core.domain.models.SessionStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommonWebsocketInteractor @Inject constructor(
    private val sessionStatusWebsocketRepository: SessionStatusWebsocketRepository,
    // Другие репозитории
) {
    fun subscribeSessionStatus(): Flow<SessionStatus> {
        return sessionStatusWebsocketRepository.subscribe()
    }

    suspend fun sendMessageSessionStatus(message: SessionStatus) {
        sessionStatusWebsocketRepository.sendMessage(message)
    }

    suspend fun unsubscribeSessionStatus() {
        sessionStatusWebsocketRepository.unsubscribe()
    }
}