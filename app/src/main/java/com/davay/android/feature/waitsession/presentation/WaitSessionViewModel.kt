package com.davay.android.feature.waitsession.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.davay.android.BuildConfig
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

    private val sessionId = "lQzwL3PC"
    private val deviceId = "d3e22dcc-1393-4171-8123-468b1c9b3c23"

    init {
        subscribeToWebsockets()
    }

    fun isFirstTimeLaunch(): Boolean {
        return waitSessionOnBoardingInteractor.isFirstTimeLaunch()
    }

    fun markFirstTimeLaunch() {
        waitSessionOnBoardingInteractor.markFirstTimeLaunch()
    }

    @Suppress("UnusedPrivateMember")
    private fun subscribeToWebsockets() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeUsers(
                    deviceId = deviceId,
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
                    deviceId = deviceId,
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
                    deviceId = deviceId,
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
                    deviceId = deviceId,
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
                    deviceId = deviceId,
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