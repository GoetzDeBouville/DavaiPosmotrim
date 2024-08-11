package com.davay.android.core.data.network

import com.davay.android.core.data.dto.SessionStatusDto
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WebsocketSessionStatusClient :
    WebsocketKtorNetworkClient<SessionStatusDto, SessionStatusDto>() {

    override fun mapIncomingMessage(message: Frame.Text, converter: Json): SessionStatusDto {
        return runCatching {
            converter.decodeFromString<SessionStatusDto>(message.readText())
        }.getOrNull() ?: SessionStatusDto.CLOSED
    }

    override fun mapSentMessageToJson(message: SessionStatusDto, converter: Json): String {
        return converter.encodeToString(message)
    }
}