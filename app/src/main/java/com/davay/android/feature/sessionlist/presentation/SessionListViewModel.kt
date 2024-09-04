package com.davay.android.feature.sessionlist.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.davay.android.BuildConfig
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.core.domain.impl.CommonWebsocketInteractor.Companion.PATH_SESSION_STATUS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionListViewModel @Inject constructor(
    private val commonWebsocketInteractor: CommonWebsocketInteractor,
) : BaseViewModel() {
    private val sessionId = "asdf123"
    private val deviceId = "d3e22dcc-1393-4171-8123-468b1c9b3c23"

    @Suppress("UnusedPrivateMember")
    private fun subscribeToSessionStatusWebsocket() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeSessionStatus(
                    deviceId = deviceId,
                    path = "$sessionId$PATH_SESSION_STATUS"
                ).collect { sessionStatus ->
                    Log.d("WaitSessionViewModel", sessionStatus.toString())
                }
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
        }

    }
}