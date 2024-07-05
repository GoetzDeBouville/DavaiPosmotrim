package com.davay.android.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.davay.android.data.database.entity.MovieDetailsEntity
import com.davay.android.data.database.entity.SessionEntity
import com.davay.android.data.database.entity.SessionMovieCrossRef
import com.davay.android.data.database.entity.SessionWithMovies

@Dao
interface HistoryDao {

    @Query("SELECT * FROM sessions")
    suspend fun getSessions(): List<SessionEntity>

    @Transaction
    @Query("SELECT * FROM sessions WHERE sessionId = :sessionId")
    suspend fun getSessionWithMovies(sessionId: String): SessionWithMovies

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: SessionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDetailsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSessionWithMovie(sessionWithMovie: SessionMovieCrossRef)
}
