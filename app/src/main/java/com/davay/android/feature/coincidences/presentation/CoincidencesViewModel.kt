package com.davay.android.feature.coincidences.presentation

import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import com.davay.android.R
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.core.domain.mockdata.api.GetData
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.core.domain.models.SessionStatus
import com.davay.android.feature.coincidences.di.GET_TEST_MOVIE_USE_CASE
import com.davay.android.feature.coincidences.domain.CoincidencesInteractor
import com.davay.android.feature.matchedsession.presentation.MatchedSessionFragment.Companion.SESSION_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class CoincidencesViewModel @Inject constructor(
    @Named(GET_TEST_MOVIE_USE_CASE)
    private val getData: GetData<MovieDetails, ErrorType>,
    private val coincidencesInteractor: CoincidencesInteractor,
    private val commonWebsocketInteractor: CommonWebsocketInteractor
) : BaseViewModel() {

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val state = _state.asStateFlow()

    init {
        getCoincidences()
        runSafelyUseCaseWithNullResponse(
            useCaseFlow = commonWebsocketInteractor.getSessionStatus(),
            onSuccess = { sessionStatus ->
                if (sessionStatus == SessionStatus.ROULETTE) {
                    navigate(R.id.action_coincidencesFragment_to_rouletteFragment)
                } else if (sessionStatus == SessionStatus.CLOSED) {
                    navigate(
                        R.id.action_coincidencesFragment_to_matchedSessionFragment,
                        bundleOf(SESSION_ID to commonWebsocketInteractor.sessionId)
                    )
                }
            },
            onFailure = { }
        )
    }

    private fun getCoincidences() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(UiState.Loading)

            getData.getData().fold(
                onSuccess = { movies ->
                    _state.value =
                        if (movies.isEmpty()) UiState.Empty else UiState.Data(data = movies)
                },
                onError = { errorType ->
                    _state.value = UiState.Error(errorType)
                }
            )
        }
    }

    @Suppress("Detekt.FunctionOnlyReturningConstant")
    fun isFirstTimeLaunch(): Boolean = coincidencesInteractor.isFirstTimeLaunch()

    fun markFirstTimeLaunch() {
        coincidencesInteractor.markFirstTimeLaunch()
    }

    fun getCoincidencesCount(): Int {
        val currentState = _state.value
        return if (currentState is UiState.Data) {
            currentState.data.size
        } else {
            0
        }
    }
}