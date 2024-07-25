package com.davay.android.feature.matchedsession.presentation

import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.mockdata.api.GetData
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.feature.coincidences.di.GET_TEST_MOVIE_USE_CASE
import com.davay.android.feature.coincidences.presentation.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

// Скопировано из совпадений, чисто для отображения. Потом сменить на правильное

class MatchedSessionViewModel @Inject constructor(
    @Named(GET_TEST_MOVIE_USE_CASE)
    private val getData: GetData<MovieDetails, ErrorType>
) : BaseViewModel() {

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val state = _state.asStateFlow()

    init {
        getCoincidences()
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
}