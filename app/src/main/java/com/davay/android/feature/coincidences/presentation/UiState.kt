package com.davay.android.feature.coincidences.presentation

import com.davay.android.feature.coincidences.ErrorType

data class UiState(
    val isLoading: Boolean = false,
    val error: ErrorType? = null,
    val movies: List<TestMovie> = emptyList()
)