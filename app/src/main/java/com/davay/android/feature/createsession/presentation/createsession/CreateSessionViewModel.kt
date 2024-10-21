package com.davay.android.feature.createsession.presentation.createsession

import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.models.SessionShort
import javax.inject.Inject

open class CreateSessionViewModel @Inject constructor() : BaseViewModel() {

    protected fun navigateToWaitSession(session: SessionShort) {
        val action = CreateSessionFragmentDirections
            .actionCreateSessionFragmentToWaitSessionFragment(session)
        navigate(action)
    }
}
