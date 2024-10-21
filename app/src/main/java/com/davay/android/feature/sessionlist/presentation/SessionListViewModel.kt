package com.davay.android.feature.sessionlist.presentation

import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.core.domain.impl.LeaveSessionUseCase
import com.davay.android.core.domain.models.ErrorScreenState
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.feature.sessionlist.domain.usecase.ConnectToSessionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    private fun subscribeToWebsockets(sessionId: String) {
        subscribeToAllWs(sessionId)
        getUsersWs()
        getSessionStatusWs()
    }

    private fun subscribeToAllWs(sessionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeWebsockets(sessionId)
            }.onFailure {
                _state.update { ConnectToSessionState.Error(ErrorScreenState.SERVER_ERROR) }
            }
        }
    }

    private fun getUsersWs() {
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.getUsers(),
            onSuccess = { list ->
                if (_state.value is ConnectToSessionState.Content && list != null) {
                    _state.update {
                        ConnectToSessionState.Content(
                            (_state.value as ConnectToSessionState.Content)
                                .session
                                .copy(users = list.map { it.name })
                        )
                    }
                }
            },
            onFailure = { error ->
                _state.update { ConnectToSessionState.Error(mapErrorToUiState(error)) }
            }
        )
    }

    private fun getSessionStatusWs() {
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.getSessionStatus(),
            onSuccess = { status ->
                when (status) {
                    SessionStatus.VOTING -> {
                        this@SessionListViewModel.sessionId = null
                        val action = SessionListFragmentDirections.actionSessionListFragmentToSelectMovieFragment()
                        navigate(action)
                    }

                    else -> {
                        // do nothing
                    }
                }
            },
            onFailure = { error ->
                _state.update { ConnectToSessionState.Error(mapErrorToUiState(error)) }
            }
        )
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
