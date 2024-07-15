package com.davay.android.feature.sessionsmatched.presentation

import com.davay.android.domain.models.ErrorType
import com.davay.android.domain.models.Session

sealed class MatchedSessionsState {
    data object Loading : MatchedSessionsState()
    data object Empty : MatchedSessionsState()
    class Content(val sessionsList: List<Session>) : MatchedSessionsState()
    class Error(val errorType: ErrorType) : MatchedSessionsState()
}