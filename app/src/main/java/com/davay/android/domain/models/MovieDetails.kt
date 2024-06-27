package com.davay.android.domain.models

data class MovieDetails(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val alternativeName: String,
    val ratingKinopoisk: Float,
    val numOfMarksKinopoisk: Int,
    val ratingImdb: Float,
    val numOfMarksImdb: Int,
    val genres: List<String>,
    val year: String,
    val countries: List<String>,
    val duration: Int,
    val description: String,
    val actors: List<String>,
    val directors: List<String>
)
