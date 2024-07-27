package com.davay.android.core.domain.models

data class SessionWithMovies(
    val session: Session,
    val movies: List<MovieDetails>
)
