package com.davay.android.feature.sessionlist.presentation

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.davay.android.BuildConfig
import com.davay.android.R
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.core.domain.impl.LeaveSessionUseCase
import com.davay.android.core.domain.models.ErrorScreenState
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.core.domain.models.converter.toSessionShort
import com.davay.android.feature.createsession.presentation.createsession.CreateSessionViewModel.Companion.SESSION_DATA
import com.davay.android.feature.sessionlist.domain.usecase.ConnectToSessionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SessionListViewModel @Inject constructor(
    private val commonWebsocketInteractor: CommonWebsocketInteractor,
    private val connectToSessionUseCase: ConnectToSessionUseCase,
    private val leaveSessionUseCase: LeaveSessionUseCase,
) : BaseViewModel() {
    private val _state = MutableStateFlow<ConnectToSessionState>(ConnectToSessionState.Loading)
    val state = _state.asStateFlow()
    private var sessionId: String? = null

    fun connectToSessionAuto(sessionId: String) {
        if (this.sessionId == null) {
            connectToSession(sessionId)
        }
    }

    fun connectToSession(sessionId: String) {
        this.sessionId = sessionId
        _state.update { ConnectToSessionState.Loading }
        runSafelyUseCase(
            useCaseFlow = connectToSessionUseCase.execute(sessionId),
            onFailure = { error ->
                _state.update { ConnectToSessionState.Error(mapErrorToUiState(error)) }
                this.sessionId = null
            },
            onSuccess = { session ->
                _state.update { ConnectToSessionState.Content(session) }
                subscribeToWebsockets(sessionId)
            }
        )
    }

    @Suppress("CyclomaticComplexMethod", "LongMethod", "CognitiveComplexMethod")
    private fun subscribeToWebsockets(sessionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeWebsockets(sessionId)
            }.onFailure {
                _state.update { ConnectToSessionState.Error(ErrorScreenState.SERVER_ERROR) }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.getUsers()
                    .collect { result ->
                        result?.fold(
                            onSuccess = { list ->
                                if (_state.value is ConnectToSessionState.Content) {
                                    _state.update {
                                        ConnectToSessionState.Content(
                                            (_state.value as ConnectToSessionState.Content)
                                                .session
                                                .copy(users = list.map { it.name })
                                        )
                                    }
                                }
                            },
                            onError = { error ->
                                _state.update { ConnectToSessionState.Error(mapErrorToUiState(error)) }
                            }
                        )
                    }
            }.onFailure { error ->
                _state.update { ConnectToSessionState.Error(ErrorScreenState.SERVER_ERROR) }
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.getSessionStatus()
                    .collect { result ->
                        result?.fold(
                            onSuccess = { status ->
                                when (status) {
                                    SessionStatus.VOTING -> {
                                        val session =
                                            (_state.value as ConnectToSessionState.Content).session.toSessionShort()
                                        val sessionJson = Json.encodeToString(session)
                                        val bundle = Bundle().apply {
                                            putString(SESSION_DATA, sessionJson)
                                        }
                                        this@SessionListViewModel.sessionId = null
                                        navigate(
                                            R.id.action_sessionListFragment_to_selectMovieFragment,
                                            bundle
                                        )
                                    }

                                    SessionStatus.CLOSED -> {
                                        leaveSessionAndNavigateBack(sessionId)
                                    }

                                    else -> {
                                        // do nothing
                                    }
                                }
                            },
                            onError = { error ->
                                _state.update { ConnectToSessionState.Error(mapErrorToUiState(error)) }
                            }
                        )
                    }
            }.onFailure { error ->
                _state.update { ConnectToSessionState.Error(ErrorScreenState.SERVER_ERROR) }
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
        }
    }

    private fun unsubscribeWebsockets() {
        viewModelScope.launch(Dispatchers.IO) {
            commonWebsocketInteractor.unsubscribeWebsockets()
        }
    }

    fun leaveSessionAndNavigateBack(sessionId: String) {
        _state.update { ConnectToSessionState.Loading }
        unsubscribeWebsockets()
        runSafelyUseCase(
            useCaseFlow = leaveSessionUseCase.execute(sessionId),
            onFailure = {
                this.sessionId = null
                navigateBack()
            },
            onSuccess = {
                this.sessionId = null
                navigateBack()
            }
        )
    }
}
