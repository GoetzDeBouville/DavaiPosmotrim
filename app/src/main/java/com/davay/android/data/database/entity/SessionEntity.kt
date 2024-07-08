package com.davay.android.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.davay.android.domain.models.User

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey val sessionId: String,
    val users: List<String>,
    val numberOfMatchedMovies: Int,
    val date: String,
    val imgUrl: String,
)
