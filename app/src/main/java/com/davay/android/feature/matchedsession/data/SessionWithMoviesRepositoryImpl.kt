package com.davay.android.feature.matchedsession.data

import com.davay.android.data.converters.getDomainMovies
import com.davay.android.data.converters.getDomainSession
import com.davay.android.data.database.HistoryDao
import com.davay.android.domain.models.MovieDetails
import com.davay.android.domain.models.Session
import com.davay.android.feature.matchedsession.domain.SessionWithMoviesRepository
import javax.inject.Inject

class SessionWithMoviesRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
) : SessionWithMoviesRepository {
    override suspend fun getSessionWithMovies(sessionId: String): Pair<Session, List<MovieDetails>>? =
        historyDao.getSessionWithMovies(sessionId)?.let {
            Pair(
                it.getDomainSession(),
                it.getDomainMovies()
            )
        }
}
