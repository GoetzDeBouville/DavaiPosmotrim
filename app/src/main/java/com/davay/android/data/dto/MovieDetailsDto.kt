package com.davay.android.data.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("year") val year: String?,
    @SerializedName("countries") val countries: List<String>?,
    @SerializedName("poster") val imgUrl: String?,
    @SerializedName("alternative_name") val alternativeName: String?,
    @SerializedName("rating_kp") val ratingKinopoisk: Float?,
    @SerializedName("rating_imdb") val ratingImdb: Float?,
    @SerializedName("votes_kp") val numOfMarksKinopoisk: Int?,
    @SerializedName("votes_imdb") val numOfMarksImdb: Int?,
    @SerializedName("movie_length") val duration: Int?,
    @SerializedName("genres") val genres: List<GenreDto>,
    @SerializedName("directors") val directors: List<String>?,
    @SerializedName("actors") val actors: List<String>?
)
