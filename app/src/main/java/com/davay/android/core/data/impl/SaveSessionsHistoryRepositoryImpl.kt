package com.davay.android.core.data.impl

import com.davay.android.core.data.converters.toDbEntity
import com.davay.android.core.data.database.HistoryDao
import com.davay.android.core.data.database.entity.SessionMovieCrossRef
import com.davay.android.core.domain.api.SaveSessionsHistoryRepository
import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.core.domain.models.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveSessionsHistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
) : SaveSessionsHistoryRepository {
    override suspend fun saveSessionsHistory(session: Session, movies: List<MovieDetails>) =
        withContext(Dispatchers.IO) {
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
}
