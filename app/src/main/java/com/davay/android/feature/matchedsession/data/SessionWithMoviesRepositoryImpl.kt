package com.davay.android.feature.matchedsession.data

import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.database.HistoryDao
import com.davay.android.core.domain.models.SessionWithMovies
import com.davay.android.feature.matchedsession.domain.SessionWithMoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SessionWithMoviesRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
) : SessionWithMoviesRepository {
    override suspend fun getSessionWithMovies(sessionId: String): SessionWithMovies? =
        withContext(Dispatchers.IO) {
            historyDao.getSessionWithMovies(sessionId)?.toDomain()
        }
}
