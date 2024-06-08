package com.davay.android.feature.coincidences.presentation

import android.net.ConnectivityManager
import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.base.usecases.GetData
import com.davay.android.feature.coincidences.ErrorType
import com.davay.android.feature.coincidences.di.GET_TEST_MOVIE_USE_CASE
import com.davay.android.utils.checkNetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class CoincidencesViewModel @Inject constructor(
    @Named(GET_TEST_MOVIE_USE_CASE)
    private val getData: GetData<TestMovie>
) : BaseViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    fun onGetData(connectivityManager: ConnectivityManager?) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(UiState.Loading)
            val hasNetworkAccess = checkNetworkState(connectivityManager)

            if (!hasNetworkAccess) {
                _uiState.emit(UiState.Error(ErrorType.NO_INTERNET))
            } else {
                getData.getData().fold(
                    onSuccess = { movies ->
                        _uiState.emit(
                            if (movies.isEmpty()) UiState.Empty
                            else UiState.Data(data = movies)
                        )
                    },
                    onFailure = { _ ->
                        _uiState.emit(UiState.Error(ErrorType.SERVER_ERROR))
                    }
                )
            }
        }
    }
}