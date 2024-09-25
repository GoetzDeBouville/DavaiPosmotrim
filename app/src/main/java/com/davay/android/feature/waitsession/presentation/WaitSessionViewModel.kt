package com.davay.android.feature.waitsession.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.davay.android.R
import com.davay.android.base.BaseViewModel
import com.davay.android.feature.waitsession.domain.api.WaitSessionOnBoardingInteractor
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.core.domain.models.Result
import com.davay.android.feature.waitsession.domain.WaitSessionOnBoardingInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WaitSessionViewModel @Inject constructor(
    private val waitSessionOnBoardingInteractor: WaitSessionOnBoardingInteractor,
    private val commonWebsocketInteractor: CommonWebsocketInteractor,
) : BaseViewModel() {

    // для теста
    private val sessionId = "Lj0mPl7q"

    init {
        subscribeToWebsockets()
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
        viewModelScope.launch(Dispatchers.IO) {
            commonWebsocketInteractor.subscribeUsers(
                sessionId = sessionId
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        Log.d("WaitSessionViewModel", result.data.toString())
                    }

                    is Result.Error -> {
                        Log.d("WaitSessionViewModel", result.error.toString())
                    }

                    null -> {
                        Log.d("WaitSessionViewModel", null.toString())
                    }
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            commonWebsocketInteractor.subscribeSessionStatus(
                sessionId = sessionId
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        Log.d("WaitSessionViewModel", result.data.toString())
                    }

                    is Result.Error -> {
                        Log.d("WaitSessionViewModel", result.error.toString())
                    }

                    null -> {
                        Log.d("WaitSessionViewModel", null.toString())
                    }
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            commonWebsocketInteractor.subscribeSessionResult(
                sessionId = sessionId
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        Log.d("WaitSessionViewModel", result.data.toString())
                    }

                    is Result.Error -> {
                        Log.d("WaitSessionViewModel", result.error.toString())
                    }

                    null -> {
                        Log.d("WaitSessionViewModel", null.toString())
                    }
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            commonWebsocketInteractor.subscribeRouletteId(
                sessionId = sessionId
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        Log.d("WaitSessionViewModel", result.data.toString())
                    }

                    is Result.Error -> {
                        Log.d("WaitSessionViewModel", result.error.toString())
                    }

                    null -> {
                        Log.d("WaitSessionViewModel", null.toString())
                    }
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            commonWebsocketInteractor.subscribeMatchesId(
                sessionId = sessionId
            ).collect { result ->
                when (result) {
                    is Result.Success -> {
                        Log.d("WaitSessionViewModel", result.data.toString())
                    }

                    is Result.Error -> {
                        Log.d("WaitSessionViewModel", result.error.toString())
                    }

                    null -> {
                        Log.d("WaitSessionViewModel", null.toString())
                    }
                }
            }
        }

    }
}