package com.davay.android.feature.coincidences.presentation

import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.base.usecases.GetData
import com.davay.android.feature.coincidences.ErrorType
import com.davay.android.feature.coincidences.di.GET_TEST_MOVIE_USE_CASE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class CoincidencesViewModel @Inject constructor(
    @Named(GET_TEST_MOVIE_USE_CASE)
    private val getData: GetData<TestMovie>
) : BaseViewModel() {

    private val _state: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val state: StateFlow<UiState>
        get() = _state

    fun getCoincidences() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(UiState.Loading)

            getData.getData().fold(
                onSuccess = { movies ->
                    val newState = if (movies.isEmpty()) UiState.Empty else UiState.Data(data = movies)
                    _state.emit(newState)
                },
                onFailure = { throwable ->
                    val errorType = if (throwable is IOException) {
                        ErrorType.NO_INTERNET
                    } else {
                        ErrorType.SERVER_ERROR
                    }
                    _state.emit(UiState.Error(errorType))
                }
            )
        }
    }
}