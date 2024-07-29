package com.davay.android.feature.coincidences.presentation

import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.MovieDetails

sealed class UiState {

    data object Empty : UiState()
    data object Loading : UiState()
    class Data(val data: List<MovieDetails> = emptyList()) : UiState()
    class Error(val errorType: ErrorType) : UiState()
}