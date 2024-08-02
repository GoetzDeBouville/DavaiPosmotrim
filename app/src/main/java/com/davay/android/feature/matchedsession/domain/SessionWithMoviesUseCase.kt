package com.davay.android.feature.matchedsession.domain

import com.davay.android.core.domain.models.SessionWithMovies
import javax.inject.Inject

class SessionWithMoviesUseCase @Inject constructor(
    private val sessionWithMoviesRepository: SessionWithMoviesRepository
) {
    suspend fun execute(sessionId: String): SessionWithMovies? {
        return sessionWithMoviesRepository.getSessionWithMovies(sessionId)
    }
}