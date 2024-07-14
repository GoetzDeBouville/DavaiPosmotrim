package com.davay.android.domain.usecases

interface GetSingleDataUseCase<T> {
    fun getSingleData(): T
}