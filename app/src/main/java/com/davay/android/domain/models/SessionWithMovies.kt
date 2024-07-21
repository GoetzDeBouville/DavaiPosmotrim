package com.davay.android.domain.models

data class SessionWithMovies(
    val session: Session,
    val movies: List<MovieDetails>
)
