package com.davay.android.feature.waitsession.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.davay.android.BuildConfig
import com.davay.android.R
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.feature.waitsession.domain.WaitSessionOnBoardingInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WaitSessionViewModel @Inject constructor(
    private val waitSessionOnBoardingInteractor: WaitSessionOnBoardingInteractor,
    private val commonWebsocketInteractor: CommonWebsocketInteractor,
) : BaseViewModel() {

    // для теста
    private val sessionId = "nm7dnwS5"

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

    // для теста
    @Suppress("LongMethod", "StringLiteralDuplication", "CognitiveComplexMethod")
    private fun subscribeToWebsockets() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeUsers(
                    sessionId = sessionId
                ).collect { list ->
                    Log.d("WaitSessionViewModel", list.toString())
                }
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeSessionStatus(
                    sessionId = sessionId
                ).collect { sessionStatus ->
                    Log.d("WaitSessionViewModel", sessionStatus.toString())
                }
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeSessionResult(
                    sessionId = sessionId
                ).collect { sessionResult ->
                    Log.d("WaitSessionViewModel", sessionResult.toString())
                }
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeRouletteId(
                    sessionId = sessionId
                ).collect { id ->
                    Log.d("WaitSessionViewModel", id.toString())
                }
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeMatchesId(
                    sessionId = sessionId
                ).collect { id ->
                    Log.d("WaitSessionViewModel", id.toString())
                }
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
        }

    }
}