package com.davay.android.data.dto

import com.google.gson.annotations.SerializedName

typealias movieId = Int

data class SessionDto(
    val id: String,
    val users: List<UserDto>,
    val movies: List<movieId>?,
    @SerializedName("matched_movies") val matchedMovies: List<movieId>?,
    val date: String,
    val status: SessionStatusDto
)