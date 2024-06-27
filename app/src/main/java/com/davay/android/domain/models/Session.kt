package com.davay.android.domain.models

data class Session(
    val id: String,
    val users: List<User>,
    val movies: List<MovieDetails>?,
    val matchedMovies: List<MovieDetails>?,
    val date: String,
    val status: String
)
