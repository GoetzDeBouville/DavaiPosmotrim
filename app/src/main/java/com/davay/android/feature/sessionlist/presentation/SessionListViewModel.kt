package com.davay.android.feature.sessionlist.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.davay.android.BuildConfig
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.core.domain.impl.CommonWebsocketInteractor.Companion.BASE_URL
import com.davay.android.core.domain.impl.CommonWebsocketInteractor.Companion.PATH_SESSION_STATUS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionListViewModel @Inject constructor(
    private val commonWebsocketInteractor: CommonWebsocketInteractor,
) : BaseViewModel() {
    private val sessionId = "asdf123"

    private fun subscribeToSessionStatusWebsocket() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeSessionStatus(
                    baseUrl = BASE_URL,
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