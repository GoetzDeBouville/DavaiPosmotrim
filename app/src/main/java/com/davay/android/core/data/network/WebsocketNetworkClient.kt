package com.davay.android.core.data.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface WebsocketNetworkClient<O> {
    val connectionState: StateFlow<Boolean>
    fun subscribe(deviceId: String, path: String): Flow<O>
    suspend fun close()
}