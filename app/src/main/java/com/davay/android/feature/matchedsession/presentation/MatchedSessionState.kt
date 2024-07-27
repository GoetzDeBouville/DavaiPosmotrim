package com.davay.android.feature.matchedsession.presentation

import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.SessionWithMovies

sealed class MatchedSessionState {

    data object Empty : MatchedSessionState()
    data object Loading : MatchedSessionState()
    class Data(val data: SessionWithMovies) : MatchedSessionState()
    class Error(val errorType: ErrorType) : MatchedSessionState()
}