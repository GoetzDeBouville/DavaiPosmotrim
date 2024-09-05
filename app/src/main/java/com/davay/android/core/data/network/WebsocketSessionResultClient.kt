package com.davay.android.core.data.network

import com.davay.android.core.data.dto.MessageSessionResultDto
import com.davay.android.core.data.dto.SessionResultDto
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json

class WebsocketSessionResultClient : WebsocketKtorNetworkClient<SessionResultDto?, String>() {

    override fun mapIncomingMessage(message: Frame.Text, converter: Json): SessionResultDto? {
        return runCatching {
            converter.decodeFromString<MessageSessionResultDto>(message.readText()).sessionResultDto
        }.getOrNull()
    }

    override fun mapSentMessageToJson(message: String, converter: Json): String {
        return message
    }
}