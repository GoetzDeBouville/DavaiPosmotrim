package com.davay.android.data.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class SessionWithMovies(
    @Embedded val session: SessionEntity,
    @Relation(
        parentColumn = "sessionId",
        entityColumn = "movieId",
        associateBy = Junction(SessionMovieCrossRef::class)
    )
    val movies: List<MovieDetailsEntity>
)
