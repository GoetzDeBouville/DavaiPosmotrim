package com.davay.android.core.data.network

import com.davay.android.core.data.dto.SessionStatusDto
import com.davay.android.core.data.dto.UserDto
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json

class WebsocketUsersClient :
    WebsocketKtorNetworkClient<List<UserDto>, String>() {

    override fun mapIncomingMessage(message: Frame.Text, converter: Json): List<UserDto> {
        return runCatching {
            converter.decodeFromString<List<UserDto>>(message.readText())
        }.getOrNull() ?: emptyList()
    }

    override fun mapSentMessageToJson(message: String, converter: Json): String {
        return message
    }
}