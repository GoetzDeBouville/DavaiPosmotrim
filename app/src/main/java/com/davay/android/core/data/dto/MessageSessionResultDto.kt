package com.davay.android.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MessageSessionResultDto(
    @SerialName("message")
    val sessionResultDto: SessionResultDto
)