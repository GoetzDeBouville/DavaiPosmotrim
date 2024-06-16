package com.davay.android.utils

import com.davay.android.feature.coincidences.ErrorType

sealed class RequestResult<T> {

    data class Success<T>(val data: T) : RequestResult<T>()

    data class Error<T>(val errorType: ErrorType) : RequestResult<T>()

    fun fold(
        onSuccess: (T) -> Unit,
        onError: (ErrorType) -> Unit
    ) {
        return when (this) {
            is Success -> onSuccess(data)
            is Error -> onError(errorType)
        }
    }
}