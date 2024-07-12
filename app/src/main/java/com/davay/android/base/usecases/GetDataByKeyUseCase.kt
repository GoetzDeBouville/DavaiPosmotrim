package com.davay.android.base.usecases

interface GetDataByKeyUseCase<T> {
    fun getSharedPreferences(key: String): T
}