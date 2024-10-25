package com.davay.android.feature.createsession.presentation.createsession

import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.core.domain.models.SessionShort
import kotlinx.coroutines.launch
import javax.inject.Inject

open class CreateSessionViewModel @Inject constructor(
    private val commonWebsocketInteractor: CommonWebsocketInteractor,
) : BaseViewModel() {

    protected fun subscribeToWebsocketsAndUpdateSessionId(
        sessionId: String,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            runCatching {
                commonWebsocketInteractor.subscribeWebsockets(sessionId)
            }.onFailure {
                onFailure.invoke()
            }
        }
    }

    protected fun navigateToWaitSession(session: SessionShort) {
        val action = CreateSessionFragmentDirections
            .actionCreateSessionFragmentToWaitSessionFragment(session)
        navigate(action)
    }
}
