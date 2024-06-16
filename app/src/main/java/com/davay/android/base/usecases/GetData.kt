package com.davay.android.base.usecases

import com.davay.android.utils.Result

interface GetData<T, E> {

    suspend fun getData(): Result<List<T>, E>
}