package com.davay.android.feature.sessionlist.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConnectMessageDto(
    @SerialName("message") val message: String
)
