package com.davay.android.core.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDetailsEntity(
    @ColumnInfo(name = "movie_id")
    @PrimaryKey val movieId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String? = null,
    @ColumnInfo(name = "year")
    val year: String? = null,
    @ColumnInfo(name = "countries")
    val countries: String? = null,
    @ColumnInfo(name = "img_url")
    val imgUrl: String? = null,
    @ColumnInfo(name = "alternative_name")
    val alternativeName: String? = null,
    @ColumnInfo(name = "rating_kinopoisk")
    val ratingKinopoisk: Float = 0.0f,
    @ColumnInfo(name = "rating_imdb")
    val ratingImdb: Float = 0.0f,
    @ColumnInfo(name = "num_of_marks_kinopoisk")
    val numOfMarksKinopoisk: Int? = null,
    @ColumnInfo(name = "num_of_marks_imdb")
    val numOfMarksImdb: Int? = null,
    @ColumnInfo(name = "duration")
    val duration: Int? = null,
    @ColumnInfo(name = "genres")
    val genres: String,
    @ColumnInfo(name = "directors")
    val directors: String? = null,
    @ColumnInfo(name = "actors")
    val actors: String? = null,
)
