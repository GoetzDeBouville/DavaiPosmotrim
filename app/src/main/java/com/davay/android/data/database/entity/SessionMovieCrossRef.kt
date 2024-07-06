package com.davay.android.data.database.entity

import androidx.room.Entity

@Entity(primaryKeys = ["sessionId", "movieId"], tableName = "session_movie")
data class SessionMovieCrossRef(
    val sessionId: String,
    val movieId: Int,
)
