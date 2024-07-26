package com.davay.android.core.data.dto

import com.google.gson.annotations.SerializedName

enum class SessionStatusDto {
    @SerializedName("waiting")
    WAITING,

    @SerializedName("voting")
    VOTING,

    @SerializedName("closed")
    CLOSED,

    @SerializedName("roulette")
    ROULETTE
}