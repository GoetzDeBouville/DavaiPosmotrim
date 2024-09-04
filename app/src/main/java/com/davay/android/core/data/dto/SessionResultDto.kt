package com.davay.android.core.data.dto

import kotlinx.serialization.SerialName

data class SessionResultDto(
    @SerialName("id") val id: String,
    @SerialName("users") val users: List<UserDto>,
    @SerialName("matched_movies") val matchedMovies: List<MovieDetailsDto>,
    @SerialName("date") val date: String,
    @SerialName("image") val imgUrl: String,
    @SerialName("matched_movies_count") val matchedMoviesCount: Int,
)
