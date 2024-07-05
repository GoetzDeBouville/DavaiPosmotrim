package com.davay.android.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.davay.android.data.database.Converters
import com.davay.android.domain.models.User

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey val id: String,
    val users: List<User>,
    val numberOfMatchedMovies: Int,
    val date: String,
    val imgUrl: String,
)
