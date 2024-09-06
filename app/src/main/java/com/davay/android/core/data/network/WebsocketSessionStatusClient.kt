package com.davay.android.core.data.network

import com.davay.android.core.data.dto.MessageSessionStatusDto
import com.davay.android.core.data.dto.SessionStatusDto
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json

class WebsocketSessionStatusClient :
    WebsocketKtorNetworkClient<SessionStatusDto>() {

    override fun mapIncomingMessage(message: Frame.Text, converter: Json): SessionStatusDto {
        return runCatching {
            converter.decodeFromString<MessageSessionStatusDto>(message.readText()).sessionStatusDto
        }.getOrNull() ?: SessionStatusDto.CLOSED
    }
}