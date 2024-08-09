package com.davay.android.core.data.database

import androidx.room.Dao
import androidx.room.Query
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
}