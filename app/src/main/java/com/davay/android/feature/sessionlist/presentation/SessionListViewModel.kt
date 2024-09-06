package com.davay.android.feature.sessionlist.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.davay.android.BuildConfig
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionListViewModel @Inject constructor(
    private val commonWebsocketInteractor: CommonWebsocketInteractor,
) : BaseViewModel() {
    private val sessionId = "7CQOtxiB"
    private val deviceId = "d3e22dcc-1393-4171-8123-468b1c9b3c23"

    // временно
    @Suppress("UnusedPrivateMember")
    private fun subscribeToWebsockets() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeUsers(
                    deviceId = deviceId,
                    sessionId = sessionId
                ).collect { list ->
                    Log.d("SessionListViewModel", list.toString())
                }
            }.onFailure { error ->
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
        }
    }
}
