package com.davay.android.feature.matchedsession.domain

import com.davay.android.domain.models.MovieDetails
import com.davay.android.domain.models.Session

data class SessionWithMovies(
    val session: Session,
    val movies: List<MovieDetails>
)
