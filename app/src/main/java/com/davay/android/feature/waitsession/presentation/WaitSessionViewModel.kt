package com.davay.android.feature.waitsession.presentation

import com.davay.android.R
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.feature.waitsession.domain.api.WaitSessionOnBoardingInteractor
import com.davay.android.feature.waitsession.domain.models.WaitSessionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class WaitSessionViewModel @Inject constructor(
    private val waitSessionOnBoardingInteractor: WaitSessionOnBoardingInteractor,
    private val commonWebsocketInteractor: CommonWebsocketInteractor,
) : BaseViewModel() {
    private val _state = MutableStateFlow<WaitSessionState>(WaitSessionState.Content(emptyList()))
    val state
        get() = _state.asStateFlow()

    // для теста
    private var sessionId: String? = null

    fun subscribeWs(id: String) {
        if (sessionId == null) {
            sessionId = id
            subscribeToWebsockets()
        }
    }

    fun isFirstTimeLaunch(): Boolean {
        return waitSessionOnBoardingInteractor.isFirstTimeLaunch()
    }

    fun markFirstTimeLaunch() {
        waitSessionOnBoardingInteractor.markFirstTimeLaunch()
    }

    /**
     * Метод необходим для обхода ошибки при возврате назад на экран создания сессии после
     * смены конфигурации устройства
     */
    fun navigateToCreateSession() {
        clearBackStackToMain()
        navigate(R.id.action_mainFragment_to_createSessionFragment)
    }

    fun navigateToNextScreen() {
        navigate(R.id.action_waitSessionFragment_to_selectMovieFragment)
    }

    // для теста
    @Suppress(
        "LongMethod",
        "StringLiteralDuplication",
        "CognitiveComplexMethod",
        "CyclomaticComplexMethod"
    )
    private fun subscribeToWebsockets() {
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.getUsers(),
            onSuccess = { users ->
                val userList = if (users.isNullOrEmpty()) {
                    emptyList()
                } else {
                    users
                }
                _state.update {
                    WaitSessionState.Content(userList.map { it.name })
                }
            },
            onFailure = { error ->
                _state.update {
                    WaitSessionState.Error(mapErrorToUiState(error))
                }
            }
        )
    }
}