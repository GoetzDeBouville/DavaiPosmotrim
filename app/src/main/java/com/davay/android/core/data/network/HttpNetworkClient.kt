package com.davay.android.core.data.network

import com.davay.android.core.data.network.model.Response

interface HttpNetworkClient<T, R> {
    suspend fun getResponse(sealedRequest: T): Response<R>
}