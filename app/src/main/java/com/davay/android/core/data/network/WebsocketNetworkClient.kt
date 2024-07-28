package com.davay.android.core.data.network

import kotlinx.coroutines.flow.Flow

interface WebsocketNetworkClient<O, M> {
    fun subscribe(): Flow<O>
    suspend fun sendMessage(message: M)
    suspend fun close()
}