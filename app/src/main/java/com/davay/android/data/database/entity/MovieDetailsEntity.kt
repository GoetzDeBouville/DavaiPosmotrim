package com.davay.android.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.davay.android.data.database.Converters
import com.davay.android.domain.models.Genre

@Entity(tableName = "movies")
data class MovieDetailsEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String?,
    val year: String?,
    val countries: List<String>,
    val imgUrl: String?,
    val alternativeName: String?,
    val ratingKinopoisk: Float?,
    val ratingImdb: Float?,
    val numOfMarksKinopoisk: Int?,
    val numOfMarksImdb: Int?,
    val duration: Int?,
    val genres: List<Genre>,
    val directors: List<String>,
    val actors: List<String>,
)
