package com.davay.android.base.usecases

import com.davay.android.utils.RequestResult

interface GetData<T> {

    suspend fun getData(): RequestResult<List<T>>
}