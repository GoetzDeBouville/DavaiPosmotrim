package com.davay.android.feature.createsession.presentation.createsession

import android.os.Bundle
import android.util.Log
import com.davay.android.BuildConfig
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
            onSuccess = { users ->
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "users -> $users")
                }
            },
            onFailure = { error ->
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "users error -> ${error.name}")
                }
            }
        )
    }

    private fun subscribeSessionStatus() {
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.subscribeSessionStatus(),
            onSuccess = { status ->
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "status-> $status")
                }
            },
            onFailure = { error ->
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "status error -> ${error.name}")
                }
            }
        )
    }

    private fun subscribeSessionResult() {
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.subscribeSessionResult(),
            onSuccess = { result ->
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "result-> $result")
                }
            },
            onFailure = { error ->
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "result error -> ${error.name}")
                }
            }
        )
    }

    private fun subscribeMatchesId() {
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.subscribeMatchesId(),
            onSuccess = { result ->
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "result-> $result")
                }
            },
            onFailure = { error ->
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "result error -> ${error.name}")
                }
            }
        )
    }

    private fun subscribeRouletteId() {
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.subscribeRouletteId(),
            onSuccess = { roulette ->
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "roulette-> $roulette")
                }
            },
            onFailure = { error ->
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "roulette error -> ${error.name}")
                }
            }
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
        val TAG = CreateSessionViewModel::class.simpleName
    }
}
