package com.davay.android.data.dto

data class SessionDto(
    val id: String,
    val users: List<UserDto>,
    val movies: List<MovieDto>,
    val matchedMovies: List<MovieDto>,
    val date: String,
    val status: String
)