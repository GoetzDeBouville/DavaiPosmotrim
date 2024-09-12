package com.davay.android.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
    @SerialName("id") val id: String,
    @SerialName("users") val users: List<UserDto>,
    @SerialName("movies") val movieIdList: List<Int>,
    @SerialName("matched_movies") val matchedMovieIdList: List<Int>,
    @SerialName("date") val date: String,
    @SerialName("status") val status: SessionStatusDto,
    @SerialName("image") val imgUrl: String?
)