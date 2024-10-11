package com.davay.android.core.domain.impl

import android.util.Log
import com.davay.android.core.domain.api.WebsocketRepository
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.core.domain.models.Session
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.core.domain.models.User
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonWebsocketInteractor @Inject constructor(
    private val websocketRepository: WebsocketRepository,
) {

    private var sessionId: String = ""

    fun updateSessionId(id: String) {
        sessionId = id
        Log.i(TAG, "updateSessionId: $id")
    }

    fun getSessionId(): String = sessionId

    fun subscribeSessionStatus(sessionId: String): StateFlow<Result<SessionStatus, ErrorType>?> {
        return websocketRepository.subscribeSessionStatus(sessionId)
    }

    fun subscribeSessionStatus(): StateFlow<Result<SessionStatus, ErrorType>?> {
        return websocketRepository.subscribeSessionStatus(sessionId)
    }

    suspend fun unsubscribeAllWebSockets() {
        websocketRepository.unsubscribeSessionStatus()
        websocketRepository.unsubscribeMatchesId()
        websocketRepository.unsubscribeRouletteId()
        websocketRepository.unsubscribeSessionResult()
        websocketRepository.unsubscribeUsers()
    }

    suspend fun unsubscribeSessionStatus() {
        websocketRepository.unsubscribeSessionStatus()
    }

    fun getSessionStatus(): StateFlow<Result<SessionStatus, ErrorType>?> =
        websocketRepository.sessionStatusStateFlow

    fun subscribeUsers(sessionId: String): StateFlow<Result<List<User>, ErrorType>?> {
        return websocketRepository.subscribeUsers(sessionId)
    }

    fun subscribeUsers(): StateFlow<Result<List<User>, ErrorType>?> {
        Log.i(TAG, "subscribeUsers sessionId -> $sessionId")

        return websocketRepository.subscribeUsers(sessionId)
    }

    suspend fun unsubscribeUsers() {
        websocketRepository.unsubscribeUsers()
    }

    fun getUsers(): StateFlow<Result<List<User>, ErrorType>?> =
        websocketRepository.usersStateFlow

    fun subscribeSessionResult(sessionId: String): StateFlow<Result<Session, ErrorType>?> {
        return websocketRepository.subscribeSessionResult(sessionId)
    }

    fun subscribeSessionResult(): StateFlow<Result<Session, ErrorType>?> {
        Log.i(TAG, "subscribeSessionResult sessionId -> $sessionId")
        return websocketRepository.subscribeSessionResult(sessionId)
    }

    suspend fun unsubscribeSessionResult() {
        websocketRepository.unsubscribeSessionResult()
    }

    fun getSessionResult(): StateFlow<Result<Session, ErrorType>?> =
        websocketRepository.sessionResultFlow

    fun subscribeRouletteId(sessionId: String): StateFlow<Result<Int, ErrorType>?> {
        return websocketRepository.subscribeRouletteId(sessionId)
    }

    fun subscribeRouletteId(): StateFlow<Result<Int, ErrorType>?> {
        Log.i(TAG, "subscribeRouletteId sessionId -> $sessionId")
        return websocketRepository.subscribeRouletteId(sessionId)
    }

    suspend fun unsubscribeRouletteId() {
        websocketRepository.unsubscribeRouletteId()
    }

    fun getRouletteId(): StateFlow<Result<Int, ErrorType>?> =
        websocketRepository.rouletteIdStateFlow

    fun subscribeMatchesId(sessionId: String): StateFlow<Result<Int, ErrorType>?> {
        return websocketRepository.subscribeMatchesId(sessionId)
    }

    fun subscribeMatchesId(): StateFlow<Result<Int, ErrorType>?> {
        Log.i(TAG, "subscribeMatchesId sessionId -> $sessionId")
        return websocketRepository.subscribeMatchesId(sessionId)
    }

    suspend fun unsubscribeMatchesId() {
        websocketRepository.unsubscribeMatchesId()
    }

    fun getMatchesId(): StateFlow<Result<Int, ErrorType>?> =
        websocketRepository.matchesIdStateFlow

    private companion object {
        val TAG = CommonWebsocketInteractor::class.simpleName
    }
}