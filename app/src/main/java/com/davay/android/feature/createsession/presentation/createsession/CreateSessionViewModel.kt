package com.davay.android.feature.createsession.presentation.createsession

import android.os.Bundle
import com.davay.android.R
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.models.Session
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

open class CreateSessionViewModel @Inject constructor() : BaseViewModel() {

    protected fun navigateToWaitSession(session: Session) {
        val sessionJson = Json.encodeToString(session)
        val bundle = Bundle().apply {
            putString(SESSION_DATA, sessionJson)
        }
        navigate(
            R.id.action_createSessionFragment_to_waitSessionFragment,
            bundle
        )
    }

    companion object {
        const val SESSION_DATA = "session_data"
    }
}
