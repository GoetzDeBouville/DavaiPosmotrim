package com.davay.android.feature.sessionlist.presentation

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.davay.android.BuildConfig
import com.davay.android.R
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
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
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {
    private val _state = MutableStateFlow<ConnectToSessionState>(ConnectToSessionState.Loading)
    val state = _state.asStateFlow()
    private val sessionId: String = savedStateHandle.get<String>(ET_CODE_KEY).toString()

    init {
        connectToSession(sessionId)
    }

    private fun connectToSession(sessionId: String) {
        _state.update { ConnectToSessionState.Loading }
        runSafelyUseCase(
            useCaseFlow = connectToSessionUseCase.execute(sessionId),
            onFailure = { error ->
                _state.update { ConnectToSessionState.Error(mapErrorToUiState(error)) }
            },
            onSuccess = { session ->
                _state.update { ConnectToSessionState.Content(session) }
                subscribeToWebsockets()
            }
        )
    }

    @Suppress("LongMethod")
    private fun subscribeToWebsockets() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeUsers(sessionId)
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
                commonWebsocketInteractor.subscribeSessionStatus(
                    sessionId = sessionId
                ).collect { result ->
                    result?.fold(
                        onSuccess = { status ->
                            when (status) {
                                SessionStatus.VOTING -> {
                                    if (_state.value is ConnectToSessionState.Content) {
                                        _state.update { ConnectToSessionState.Loading }
                                        val session =
                                            (_state.value as ConnectToSessionState.Content).session.toSessionShort()
                                        val sessionJson = Json.encodeToString(session)
                                        val bundle = Bundle().apply {
                                            putString(SESSION_DATA, sessionJson)
                                        }
                                        navigate(
                                            R.id.action_sessionListFragment_to_selectMovieFragment,
                                            bundle
                                        )
                                    }
                                }

                                SessionStatus.CLOSED -> {
                                    _state.update { ConnectToSessionState.Loading }
                                    navigateBack()
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
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeSessionResult(sessionId = sessionId)
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeMatchesId(sessionId = sessionId)
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeRouletteId(sessionId = sessionId)
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
        }
    }

    fun unsubscribeWebsockets() {
        viewModelScope.launch(Dispatchers.IO) {
            commonWebsocketInteractor.unsubscribeUsers()
            commonWebsocketInteractor.unsubscribeSessionStatus()
            commonWebsocketInteractor.unsubscribeSessionResult()
            commonWebsocketInteractor.unsubscribeMatchesId()
            commonWebsocketInteractor.unsubscribeRouletteId()
        }
    }

    companion object {
        private const val ET_CODE_KEY = "ET_CODE_KEY"
    }
}
