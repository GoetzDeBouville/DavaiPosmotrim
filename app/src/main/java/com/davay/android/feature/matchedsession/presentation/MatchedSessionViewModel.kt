package com.davay.android.feature.matchedsession.presentation

import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.models.ErrorScreenState
import com.davay.android.feature.matchedsession.domain.SessionWithMoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MatchedSessionViewModel @Inject constructor(
    private val sessionWithMoviesRepository: SessionWithMoviesRepository
) : BaseViewModel() {

    private val _state: MutableStateFlow<MatchedSessionState> =
        MutableStateFlow(MatchedSessionState.Error(ErrorScreenState.EMPTY))
    val state = _state.asStateFlow()

    fun getSessionData(sessionId: String) {
        _state.value = MatchedSessionState.Loading
        viewModelScope.launch {
            val sessionWithMovies = sessionWithMoviesRepository.getSessionWithMovies(sessionId)
            when {
                sessionWithMovies == null -> _state.value =
                    MatchedSessionState.Error(ErrorScreenState.EMPTY)

                sessionWithMovies.movies.isEmpty() -> _state.value =
                    MatchedSessionState.Empty(sessionWithMovies)

                else -> _state.value = MatchedSessionState.Data(sessionWithMovies)
            }
        }
    }
}
