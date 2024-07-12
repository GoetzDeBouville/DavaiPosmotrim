package com.davay.android.domain.usecases

interface GetDataByKeyUseCase<T> {
    fun getSharedPreferences(key: String): T
}