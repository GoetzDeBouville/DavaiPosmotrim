package com.davay.android.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String?,
    @SerialName("year") val year: String?,
    @SerialName("countries") val countries: List<String>?,
    @SerialName("poster") val imgUrl: String?,
    @SerialName("alternative_name") val alternativeName: String?,
    @SerialName("rating_kp") val ratingKinopoisk: Float,
    @SerialName("rating_imdb") val ratingImdb: Float,
    @SerialName("votes_kp") val numOfMarksKinopoisk: Int,
    @SerialName("votes_imdb") val numOfMarksImdb: Int?,
    @SerialName("movie_length") val duration: Int?,
    @SerialName("genres") val genres: List<GenreDto>,
    @SerialName("directors") val directors: List<String>?,
    @SerialName("actors") val actors: List<String>?
)
