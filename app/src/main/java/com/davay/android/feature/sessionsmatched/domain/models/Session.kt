package com.davay.android.feature.sessionsmatched.domain.models

import com.davay.android.feature.selectmovie.domain.models.MovieDetailsDemo

data class Session(
    val id: String,
    val users: List<User>,
    val movies: List<MovieDetailsDemo>,
    val matchedMovies: List<MovieDetailsDemo>,
    val date: String,
    val status: String
)
