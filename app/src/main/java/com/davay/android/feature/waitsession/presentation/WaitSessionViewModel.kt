package com.davay.android.feature.waitsession.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.feature.waitsession.domain.api.WaitSessionOnBoardingInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class WaitSessionViewModel @Inject constructor(
    private val waitSessionOnBoardingInteractor: WaitSessionOnBoardingInteractor,
    private val commonWebsocketInteractor: CommonWebsocketInteractor,
) : BaseViewModel() {

    // для теста
    private var sessionId: String? = null

    fun subscribeWs(id: String) {
        if (sessionId == null) {
            sessionId = id
            subscribeToWebsockets(id)
        }
    }

    fun isFirstTimeLaunch(): Boolean {
        return waitSessionOnBoardingInteractor.isFirstTimeLaunch()
    }

    fun markFirstTimeLaunch() {
        waitSessionOnBoardingInteractor.markFirstTimeLaunch()
    }

    fun navigateToNextScreen() {
        val action = WaitSessionFragmentDirections.actionWaitSessionFragmentToSelectMovieFragment()
        navigate(action)
    }

    // для теста
    private fun subscribeToWebsockets(sessionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.subscribeWebsockets(sessionId)
            }.onFailure {
                Log.d("WaitSessionViewModel", "Ошибка подключения к сокетам")
            }
        }
    }
}