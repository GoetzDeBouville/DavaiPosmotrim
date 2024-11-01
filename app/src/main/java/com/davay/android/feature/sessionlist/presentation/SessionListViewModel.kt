package com.davay.android.feature.sessionlist.presentation

import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.core.domain.impl.LeaveSessionUseCase
import com.davay.android.core.domain.models.ErrorScreenState
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.core.domain.models.UserDataFields
import com.davay.android.core.domain.models.converter.toSessionShort
import com.davay.android.core.domain.usecases.GetUserDataUseCase
import com.davay.android.feature.onboarding.presentation.OnboardingFragment
import com.davay.android.feature.sessionlist.domain.usecase.ConnectToSessionUseCase
import com.davay.android.feature.waitsession.domain.api.WaitSessionOnBoardingInteractor
import com.davay.android.utils.SorterList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionListViewModel @Inject constructor(
    private val waitSessionOnBoardingInteractor: WaitSessionOnBoardingInteractor,
    private val commonWebsocketInteractor: CommonWebsocketInteractor,
    private val connectToSessionUseCase: ConnectToSessionUseCase,
    private val leaveSessionUseCase: LeaveSessionUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val sorterList: SorterList,
) : BaseViewModel() {
    private val _state = MutableStateFlow<ConnectToSessionState>(ConnectToSessionState.Loading)
    val state = _state.asStateFlow()
    private var sessionId: String = ""

    fun connectToSessionAuto(sessionId: String) {
        if (this.sessionId.isEmpty()) {
            connectToSession(sessionId)
        }
    }

    /**
     * Подключает к сессии и обновляет значение sessionId в commonWebsocketInteractor
     */
    fun connectToSession(sessionId: String) {
        this.sessionId = sessionId
        _state.update { ConnectToSessionState.Loading }
        runSafelyUseCase(
            useCaseFlow = connectToSessionUseCase.execute(sessionId),
            onFailure = { error ->
                _state.update { ConnectToSessionState.Error(mapErrorToUiState(error)) }
                this.sessionId = ""
            },
            onSuccess = { session ->
                val userName = getUserDataUseCase.getUserData(UserDataFields.UserName())
                _state.update {
                    ConnectToSessionState.Content(
                        session.copy(
                            users = sorterList.sortStringUserList(
                                session.users,
                                userName
                            )
                        )
                    )
                }
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
                        val session =
                            (_state.value as ConnectToSessionState.Content).session.toSessionShort()
                        this@SessionListViewModel.sessionId = ""

                        val action = if (isFirstTimeLaunch()) {
                            markFirstTimeLaunch()
                            SessionListFragmentDirections.actionSessionListFragmentToOnboardingFragment(
                                OnboardingFragment.ONBOARDING_INSTRUCTION_SET
                            )
                        } else {
                            SessionListFragmentDirections.actionSessionListFragmentToSelectMovieFragment(
                                session
                            )
                        }
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

    private fun isFirstTimeLaunch(): Boolean {
        return waitSessionOnBoardingInteractor.isFirstTimeLaunch()
    }

    private fun markFirstTimeLaunch() {
        waitSessionOnBoardingInteractor.markFirstTimeLaunch()
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
                this.sessionId = ""
                navigateBack()
            },
            onSuccess = {
                this.sessionId = ""
                navigateBack()
            }
        )
    }
}
