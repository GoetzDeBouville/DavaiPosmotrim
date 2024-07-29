package com.davay.android.core.domain.models

sealed class Result<T, E> {

    data class Success<T, E>(val data: T) : Result<T, E>()

    data class Error<T, E>(val error: E) : Result<T, E>()

    fun fold(
        onSuccess: (T) -> Unit,
        onError: (E) -> Unit
    ) {
        return when (this) {
            is Success -> onSuccess(data)
            is Error -> onError(error)
        }
    }
}