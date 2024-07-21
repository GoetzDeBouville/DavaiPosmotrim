package com.davay.android.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class SessionEntity(
    @ColumnInfo(name = "session_id")
    @PrimaryKey val sessionId: String,
    @ColumnInfo(name = "users")
    val users: String,
    @ColumnInfo(name = "number_of_matched_movies")
    val numberOfMatchedMovies: Int,
    @ColumnInfo(name = "date")
    val date: Long,
    @ColumnInfo(name = "img_url")
    val imgUrl: String,
)
