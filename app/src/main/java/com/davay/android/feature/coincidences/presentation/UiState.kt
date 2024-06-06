package com.davay.android.feature.coincidences.presentation

import com.davay.android.feature.coincidences.ErrorType

sealed class UiState {

    data object Empty: UiState()
    data object Loading : UiState()
    data class Data(val data: List<TestMovie> = emptyList()) : UiState()
    data class Error(val errorType: ErrorType) : UiState()
}