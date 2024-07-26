package com.davay.android.feature.matchedsession.presentation

import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.domain.models.ErrorType
import com.davay.android.feature.matchedsession.domain.SessionWithMoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MatchedSessionViewModel @Inject constructor(
    private val sessionWithMoviesRepository: SessionWithMoviesRepository
) : BaseViewModel() {

    private val _state: MutableStateFlow<MatchedSessionState> =
        MutableStateFlow(MatchedSessionState.Loading)
    val state = _state.asStateFlow()

    fun getSessionData(sessionId: String) {
        _state.value = MatchedSessionState.Loading
        viewModelScope.launch {
            val pair = sessionWithMoviesRepository.getSessionWithMovies(sessionId)
            if (pair == null) {
                _state.value = MatchedSessionState.Error(ErrorType.NOT_FOUND)
            } else {
                _state.value = MatchedSessionState.Data(session = pair.first, movies = pair.second)
            }
        }
    }
}
