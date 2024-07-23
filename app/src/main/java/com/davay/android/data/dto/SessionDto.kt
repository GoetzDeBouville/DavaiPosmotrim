package com.davay.android.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionDto(
    @SerialName("id") val id: String,
    @SerialName("users") val users: List<UserDto>,
    @SerialName("matched_movies") val numberOfMatchedMovies: Int?,
    @SerialName("date") val date: String,
    @SerialName("status") val status: SessionStatusDto,
    @SerialName("session_img") val imgUrl: String
)