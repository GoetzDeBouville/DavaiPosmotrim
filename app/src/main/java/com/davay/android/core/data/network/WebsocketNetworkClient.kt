package com.davay.android.core.data.network

import kotlinx.coroutines.flow.Flow

interface WebsocketNetworkClient<O> {
    fun subscribe(deviceId: String, path: String): Flow<O>
    suspend fun close()
}