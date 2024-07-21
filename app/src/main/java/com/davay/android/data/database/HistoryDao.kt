package com.davay.android.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.davay.android.data.database.entity.MovieDetailsEntity
import com.davay.android.data.database.entity.SessionEntity
import com.davay.android.data.database.entity.SessionMovieCrossRef
import com.davay.android.data.database.entity.SessionWithMovies

@Dao
interface HistoryDao {

    // Получение всех сохраненных сессий
    @Query("SELECT * FROM sessions")
    suspend fun getSessions(): List<SessionEntity>

    // Получение по sessionId сессии с её списком фильмов. Если такой сессии нет, то null
    @Transaction
    @Query("SELECT * FROM sessions WHERE session_id = :sessionId")
    suspend fun getSessionWithMovies(sessionId: String): SessionWithMovies?

    // Сохранение сессии (без фильмов)
    @Upsert
    suspend fun insertSession(session: SessionEntity)

    // Сохранение фильма
    @Upsert
    suspend fun insertMovie(movie: MovieDetailsEntity)

    // Сохранение связи сессии и фильма с помощью sessionId и movieId
    @Upsert
    suspend fun insertSessionMovieReference(sessionMovieCrossRef: SessionMovieCrossRef)
}
