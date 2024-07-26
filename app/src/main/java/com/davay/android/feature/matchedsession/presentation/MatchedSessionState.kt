package com.davay.android.feature.matchedsession.presentation

import com.davay.android.domain.models.ErrorType
import com.davay.android.domain.models.MovieDetails
import com.davay.android.domain.models.Session

sealed interface MatchedSessionState {
    object Loading : MatchedSessionState
    class Data(val session: Session, val movies: List<MovieDetails>) : MatchedSessionState
    class Error(val errorType: ErrorType) : MatchedSessionState
}
