package com.davay.android.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["session_id", "movie_id"], tableName = "session_movie")
data class SessionMovieCrossRef(
    @ColumnInfo(name = "session_id")
    val sessionId: String,
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
)
