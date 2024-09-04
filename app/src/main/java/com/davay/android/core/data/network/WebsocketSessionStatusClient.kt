package com.davay.android.core.data.network

import com.davay.android.core.data.dto.SessionStatusDto
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json

class WebsocketSessionStatusClient :
    WebsocketKtorNetworkClient<SessionStatusDto, String>() {

    override fun mapIncomingMessage(message: Frame.Text, converter: Json): SessionStatusDto {
        return runCatching {
            converter.decodeFromString<SessionStatusDto>(message.readText())
        }.getOrNull() ?: SessionStatusDto.CLOSED
    }

    override fun mapSentMessageToJson(message: String, converter: Json): String {
        return message
    }
}