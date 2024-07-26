package com.davay.android.feature.matchedsession.domain

import com.davay.android.domain.models.MovieDetails
import com.davay.android.domain.models.Session

interface SessionWithMoviesRepository {
    suspend fun getSessionWithMovies(sessionId: String): Pair<Session, List<MovieDetails>>?
}