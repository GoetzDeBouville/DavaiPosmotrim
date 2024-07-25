package com.davay.android.feature.selectmovie.data

import com.davay.android.data.converters.toDbEntity
import com.davay.android.data.database.HistoryDao
import com.davay.android.data.database.entity.SessionMovieCrossRef
import com.davay.android.domain.models.MovieDetails
import com.davay.android.domain.models.Session
import com.davay.android.feature.selectmovie.domain.SaveSessionsHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveSessionsHistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
) : SaveSessionsHistoryRepository {
    override suspend fun saveSessionsHistory(session: Session, movies: List<MovieDetails>) =
        withContext(Dispatchers.IO) {
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
        }
}
