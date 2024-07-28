package com.davay.android.feature.matchedsession.domain

import com.davay.android.core.domain.models.SessionWithMovies

interface SessionWithMoviesRepository {
    suspend fun getSessionWithMovies(sessionId: String): SessionWithMovies?
}