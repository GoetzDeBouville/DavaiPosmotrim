package com.davay.android.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey val sessionId: String,
    val users: List<String>,
    val numberOfMatchedMovies: Int,
    val date: Long,
    val imgUrl: String,
)
