package com.davay.android.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.send
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

abstract class WebsocketKtorNetworkClient<O, M> : WebsocketNetworkClient<O, M> {
    private val httpClient = HttpClient {
        install(WebSockets) {
            pingInterval = PING_INTERVAL
        }
    }
    private var session: WebSocketSession? = null

    override suspend fun close() {
        session?.close()
        session = null
    }

    override fun subscribe(baseUrl: String, path: String): Flow<O> = flow {
        session = httpClient.webSocketSession(host = baseUrl, path = path)
        session?.let {
            val incomingMessageFlow = it.incoming.consumeAsFlow()
                .filterIsInstance<Frame.Text>()
                .map { frame -> mapIncomingMessage(frame, Json) }
            emitAll(incomingMessageFlow)
        }
    }

    abstract fun mapIncomingMessage(message: Frame.Text, converter: Json): O
    abstract fun mapSentMessageToJson(message: M, converter: Json): String

    override suspend fun sendMessage(message: M) {
        session?.send(mapSentMessageToJson(message, Json))
    }

    companion object {
        const val PING_INTERVAL = 20_000L
    }
}