package com.davay.android.base.usecases

interface GetData<T> {

    suspend fun getData(): Result<List<T>>
}