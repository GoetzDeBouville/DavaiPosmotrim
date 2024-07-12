package com.davay.android.base.usecases

interface SetDataByKeyUseCase<T> {
    fun setSharedPreferences(key: String, value: T)
}