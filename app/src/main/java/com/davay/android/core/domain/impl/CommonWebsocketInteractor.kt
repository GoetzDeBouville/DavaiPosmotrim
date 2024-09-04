package com.davay.android.core.domain.impl

import com.davay.android.core.domain.api.SessionStatusWebsocketRepository
import com.davay.android.core.domain.api.UsersWebsocketRepository
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.core.domain.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommonWebsocketInteractor @Inject constructor(
    private val sessionStatusWebsocketRepository: SessionStatusWebsocketRepository,
    private val usersWebsocketRepository: UsersWebsocketRepository
) {
    fun subscribeSessionStatus(deviceId: String, path: String): Flow<SessionStatus> {
        return sessionStatusWebsocketRepository.subscribe(deviceId, path)
    }

    suspend fun sendMessageSessionStatus(message: SessionStatus) {
        sessionStatusWebsocketRepository.sendMessage(message)
    }

    suspend fun unsubscribeSessionStatus() {
        sessionStatusWebsocketRepository.unsubscribe()
    }

    fun subscribeUsers(deviceId: String, path: String): Flow<List<User>> {
        return usersWebsocketRepository.subscribe(deviceId, path)
    }

    suspend fun unsubscribeUsers() {
        usersWebsocketRepository.unsubscribe()
    }

    companion object {
        const val PATH_SESSION_STATUS = "/session_status/"
        const val PATH_USERS = "/users/"
    }
}