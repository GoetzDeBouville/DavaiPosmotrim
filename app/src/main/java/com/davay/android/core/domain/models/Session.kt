package com.davay.android.core.domain.models

import com.davay.android.extensions.timeStamp
import kotlinx.serialization.Serializable

typealias userName = String

@Serializable
data class Session(
    val id: String,
    val users: List<userName>,
    val movieIdList: List<Int>,
    val matchedMovieIdList: List<Int>,
    val date: timeStamp,
    val status: SessionStatus,
    val imgUrl: String
)
