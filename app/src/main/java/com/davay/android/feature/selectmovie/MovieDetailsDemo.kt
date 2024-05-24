package com.davay.android.feature.selectmovie

import android.content.Context
import com.davay.android.R

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
) {
    fun buildStringYearCountriesRuntime(context: Context): String {
        val str = StringBuilder()
        year?.let {
            str.append(it)
        }
        if (countries.isNotEmpty()) {
            val countryList = countries.subList(0, 2)
            val countriesString = countryList.joinToString {
                COMMA_DELIMETER
            }
            str.append(DOT_DELIMETER)
            str.append(countriesString)
            if (countryList.size > MAX_COUNTRY_NUMBER) {
                str.append(MULTIPOINT)
            }
        }
        movieLengthMin?.let {
            str.append(DOT_DELIMETER)
            str.append(formatMovieDuration(movieLengthMin, context))
        }
        return str.toString()
    }

    private fun formatMovieDuration(minutes: Int, context: Context): String {
        val hours = minutes / MINUTES_NUMBER_IN_HOUR
        val remainingMinutes = minutes % MINUTES_NUMBER_IN_HOUR

        return when {
            hours > 0 && remainingMinutes > 0 -> context.getString(
                R.string.select_movies_hours_and_minutes,
                hours,
                remainingMinutes
            )

            hours > 0 -> context.getString(R.string.select_movies_hours, hours)
            else -> context.getString(R.string.select_movies_minutes, remainingMinutes)
        }
    }

    private companion object {
        const val DOT_DELIMETER = " ∙ "
        const val COMMA_DELIMETER = " ,"
        const val MULTIPOINT = "..."
        const val MAX_COUNTRY_NUMBER = 3
        const val MINUTES_NUMBER_IN_HOUR = 60
    }
}