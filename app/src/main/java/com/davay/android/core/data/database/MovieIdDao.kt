package com.davay.android.core.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.davay.android.core.data.database.entity.MovieIdEntity

@Dao
interface MovieIdDao {

    @Upsert
    suspend fun insertMovieId(movieIdEntity: MovieIdEntity)

    @Query("SELECT * FROM movie_ids")
    suspend fun getAllMovieIds(): List<MovieIdEntity>

    @Query("DELETE FROM movie_ids")
    suspend fun clearMovieIdsTable()

    /**
     * Сброс автогенерируемых id
     */
    @Query("DELETE FROM sqlite_sequence WHERE name='movie_ids'")
    suspend fun resetAutoIncrement()

    @Transaction
    suspend fun clearAndResetTable() {
        clearMovieIdsTable()
        resetAutoIncrement()
    }

    @Query("SELECT * FROM movie_ids LIMIT 1 OFFSET :position")
    suspend fun getMovieIdByPosition(position: Int): MovieIdEntity?

    @Query("SELECT COUNT(*) FROM movie_ids")
    suspend fun getMovieIdsCount(): Int

    @Query("SELECT movie_id FROM movie_ids LIMIT :limit OFFSET :offset")
    suspend fun getMovieIdsByPositionRange(offset: Int, limit: Int): List<Int>
}