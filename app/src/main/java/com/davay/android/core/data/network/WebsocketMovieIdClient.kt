package com.davay.android.core.data.network

import com.davay.android.core.data.dto.MessageMovieIdDto
import com.davay.android.core.data.dto.MessageSessionResultDto
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json

class WebsocketMovieIdClient : WebsocketKtorNetworkClient<Int?, String>() {

    override fun mapIncomingMessage(message: Frame.Text, converter: Json): Int? {
        return runCatching {
            converter.decodeFromString<MessageMovieIdDto>(message.readText()).movieId
        }.getOrNull()
    }

    override fun mapSentMessageToJson(message: String, converter: Json): String {
        return message
    }
}