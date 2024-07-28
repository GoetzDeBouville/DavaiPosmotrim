package com.davay.android.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SessionStatusDto {
    @SerialName("waiting")
    WAITING,

    @SerialName("voting")
    VOTING,

    @SerialName("closed")
    CLOSED,

    @SerialName("roulette")
    ROULETTE
}