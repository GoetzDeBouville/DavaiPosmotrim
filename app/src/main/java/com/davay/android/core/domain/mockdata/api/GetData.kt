package com.davay.android.core.domain.mockdata.api

import com.davay.android.core.domain.models.Result

interface GetData<T, E> {

    suspend fun getData(): Result<List<T>, E>
}