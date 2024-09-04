package com.davay.android.core.domain.impl

import com.davay.android.core.domain.api.SessionResultWebsocketRepository
import com.davay.android.core.domain.api.SessionStatusWebsocketRepository
import com.davay.android.core.domain.api.UsersWebsocketRepository
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.core.domain.models.SessionWithMovies
import com.davay.android.core.domain.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommonWebsocketInteractor @Inject constructor(
    private val sessionStatusWebsocketRepository: SessionStatusWebsocketRepository,
    private val usersWebsocketRepository: UsersWebsocketRepository,
    private val sessionResultWebsocketRepository: SessionResultWebsocketRepository,
) {
    fun subscribeSessionStatus(deviceId: String, sessionId: String): Flow<SessionStatus> {
        return sessionStatusWebsocketRepository.subscribe(
            deviceId,
            "$sessionId$PATH_SESSION_STATUS"
        )
    }

    suspend fun unsubscribeSessionStatus() {
        sessionStatusWebsocketRepository.unsubscribe()
    }

    fun subscribeUsers(deviceId: String, sessionId: String): Flow<List<User>> {
        return usersWebsocketRepository.subscribe(deviceId, "$sessionId$PATH_USERS")
    }

    suspend fun unsubscribeUsers() {
        usersWebsocketRepository.unsubscribe()
    }

    fun subscribeSessionResult(deviceId: String, sessionId: String): Flow<SessionWithMovies?> {
        return sessionResultWebsocketRepository.subscribe(
            deviceId,
            "$sessionId$PATH_SESSION_RESULT"
        )
    }

    suspend fun unsubscribeSessionResult() {
        sessionResultWebsocketRepository.unsubscribe()
    }

    companion object {
        const val PATH_SESSION_STATUS = "/session_status/"
        const val PATH_USERS = "/users/"
        const val PATH_SESSION_RESULT = "/session_result/"
    }
}