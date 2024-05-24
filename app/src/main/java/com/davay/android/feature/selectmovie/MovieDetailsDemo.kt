package com.davay.android.feature.selectmovie

// используется для demo
data class MovieDetailsDemo(
    val kinopoiskId: Int? = null,
    val imdbId: Int? = null,
    val movieName: String,
    val alternativeName: String? = null,
    val englishName: String? = null,
    val year: Int? = null,
    val description: String? = null,
    val shortDescription: String? = null,
    val ratingKinopoisk: Float? = null,
    val votesKinopoisk: Int? = null,
    val ratingImdb: Float? = null,
    val votesImdb: Int? = null,
    val movieLengthMin: Int? = null,
    val posterUrl: String? = null,
    val genres: List<String> = emptyList(),
    val countries: List<String> = emptyList(),
    val topCast: List<String> = emptyList(),
    val directors: List<String> = emptyList()
)