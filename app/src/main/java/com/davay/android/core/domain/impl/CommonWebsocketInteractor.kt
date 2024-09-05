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
    fun subscribeSessionStatus(deviceId: String, sessionId: String): Flow<SessionStatus> {
        return websocketRepository.subscribeSessionStatus(
            deviceId,
            "$sessionId$PATH_SESSION_STATUS"
        )
    }

    suspend fun unsubscribeSessionStatus() {
        websocketRepository.unsubscribeSessionStatus()
    }

    fun subscribeUsers(deviceId: String, sessionId: String): Flow<List<User>> {
        return websocketRepository.subscribeUsers(deviceId, "$sessionId$PATH_USERS")
    }

    suspend fun unsubscribeUsers() {
        websocketRepository.unsubscribeUsers()
    }

    fun subscribeSessionResult(deviceId: String, sessionId: String): Flow<SessionWithMovies?> {
        return websocketRepository.subscribeSessionResult(
            deviceId,
            "$sessionId$PATH_SESSION_RESULT"
        )
    }

    suspend fun unsubscribeSessionResult() {
        websocketRepository.unsubscribeSessionResult()
    }

    companion object {
        const val PATH_SESSION_STATUS = "/session_status/"
        const val PATH_USERS = "/users/"
        const val PATH_SESSION_RESULT = "/session_result/"
    }
}