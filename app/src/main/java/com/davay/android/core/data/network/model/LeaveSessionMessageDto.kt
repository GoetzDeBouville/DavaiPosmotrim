package com.davay.android.core.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeaveSessionMessageDto(
    @SerialName("message") val message: String
)
