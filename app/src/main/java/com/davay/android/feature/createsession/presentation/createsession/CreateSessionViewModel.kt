package com.davay.android.feature.createsession.presentation.createsession

import android.os.Bundle
import com.davay.android.R
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.core.domain.models.SessionShort
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

open class CreateSessionViewModel @Inject constructor(
    private val commonWebsocketInteractor: CommonWebsocketInteractor,
) : BaseViewModel() {

    protected fun subscribeToWebsocketsAndUpdateSessionId(sessionId: String) {
        commonWebsocketInteractor.updateSessionId(sessionId)
        subscribeToUsers()
        subscribeSessionStatus()
        subscribeSessionResult()
        subscribeMatchesId()
        subscribeRouletteId()
    }

    private fun subscribeToUsers() {
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.subscribeUsers(),
            onSuccess = {},
            onFailure = {}
        )
    }

    private fun subscribeSessionStatus() {
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.subscribeSessionStatus(),
            onSuccess = {},
            onFailure = {}
        )
    }

    private fun subscribeSessionResult() {
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.subscribeSessionResult(),
            onSuccess = {},
            onFailure = {}
        )
    }

    private fun subscribeMatchesId() {
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.subscribeMatchesId(),
            onSuccess = {},
            onFailure = {}
        )
    }

    private fun subscribeRouletteId() {
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.subscribeRouletteId(),
            onSuccess = {},
            onFailure = {}
        )
    }

    protected fun navigateToWaitSession(session: SessionShort) {
        val sessionJson = Json.encodeToString(session)
        val bundle = Bundle().apply {
            putString(SESSION_DATA, sessionJson)
        }
        navigate(
            R.id.action_createSessionFragment_to_waitSessionFragment,
            bundle
        )
    }

    companion object {
        const val SESSION_DATA = "session_data"
    }
}
