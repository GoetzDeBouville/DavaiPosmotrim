package com.davay.android.core.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["session_id", "movie_id"],
    tableName = "session_movie",
    indices = [Index(value = ["movie_id"])]
)
data class SessionMovieCrossRef(
    @ColumnInfo(name = "session_id")
    val sessionId: String,
    @ColumnInfo(name = "movie_id")
    val movieId: Int,
)
