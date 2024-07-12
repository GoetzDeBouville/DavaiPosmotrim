package com.davay.android.domain.usecases

interface SetDataByKeyUseCase<T> {
    fun setSharedPreferences(key: String, value: T)
}