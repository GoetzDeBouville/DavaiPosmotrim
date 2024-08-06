package com.davay.android.core.data.impl

import com.davay.android.core.data.converters.toDbEntity
import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.database.HistoryDao
import com.davay.android.core.data.database.entity.SessionMovieCrossRef
import com.davay.android.core.domain.api.SessionsHistoryRepository
import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.core.domain.models.Session
import com.davay.android.core.domain.models.SessionWithMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SessionsHistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
) : SessionsHistoryRepository {

    override suspend fun saveSessionsHistory(session: Session, movies: List<MovieDetails>) =
        withContext(Dispatchers.IO) {
            @Suppress("TooGenericExceptionCaught")
            try {
                historyDao.insertSession(session.toDbEntity())
                movies.forEach {
                    historyDao.insertMovie(it.toDbEntity())
                    historyDao.insertSessionMovieReference(
                        SessionMovieCrossRef(
                            sessionId = session.id,
                            movieId = it.id
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    override suspend fun getSessionWithMovies(sessionId: String): SessionWithMovies? =
        withContext(Dispatchers.IO) {
            @Suppress("TooGenericExceptionCaught")
            try {
                historyDao.getSessionWithMovies(sessionId).toDomain()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    override suspend fun getSessionsHistory(): List<Session>? =
        withContext(Dispatchers.IO) {
            @Suppress("TooGenericExceptionCaught")
            try {
                historyDao.getSessions().map { it.toDomain() }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
}