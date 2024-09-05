package com.davay.android.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MessageMovieIdDto (
    @SerialName("message")
    val movieId: Int
)