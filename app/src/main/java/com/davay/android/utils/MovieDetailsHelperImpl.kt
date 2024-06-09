package com.davay.android.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.davay.android.R
import com.davay.android.feature.selectmovie.domain.models.MovieDetailsDemo
import com.google.android.flexbox.FlexboxLayout

/**
 * Добавляем класс для избегания дублирования логики
 */
open class MovieDetailsHelperImpl : MovieDetailsHelper {
    override fun setImage(img: ImageView, url: String?) {
        img.load(url) {
            placeholder(com.davai.uikit.R.drawable.placeholder_general_80)
                .scale(Scale.FIT)
            error(R.drawable.ic_movie_selection_error_332)
                .scale(Scale.FIT)
            transformations(RoundedCornersTransformation())
                .crossfade(true)
        }
    }

    override fun setRateText(tvRate: TextView, ratingKinopoisk: Float?, context: Context) {
        ratingKinopoisk?.let {
            val textColor = if (it >= GOOD_RATE_7) {
                context.getColor(com.davai.uikit.R.color.done)
            } else {
                context.getColor(com.davai.uikit.R.color.attention)
            }
            tvRate.apply {
                text = it.toString()
                setTextColor(textColor)
            }
        }
    }

    override fun addGenreList(flexBox: FlexboxLayout, genres: List<String>, context: Context) {
        flexBox.removeAllViews()
        genres.forEach {
            val genreView = LayoutInflater.from(context)
                .inflate(
                    R.layout.item_genre,
                    flexBox,
                    false
                ) as TextView
            genreView.text = it
            flexBox.addView(genreView)
        }
    }

    override fun buildStringYearCountriesRuntime(data: MovieDetailsDemo, context: Context): String {
        with(data) {
            val str = StringBuilder()
            year?.let {
                str.append(it)
                str.append(DOT_DELIMETER)
            }
            if (countries.isNotEmpty()) {
                val countryList = countries.take(MAX_COUNTRY_NUMBER)
                val countriesString = countryList.joinToString(separator = DOT_DELIMETER)
                str.append(countriesString)
                if (countries.size > MAX_COUNTRY_NUMBER) {
                    str.append(MULTIPOINT)
                }
                str.append(DOT_DELIMETER)
            }
            movieLengthMin?.let {
                str.append(movieLengthMin.formatMovieDuration(context))
            }
            return str.toString()
        }
    }

    fun Int.formatMovieDuration(context: Context): String {
        val hours = this / MINUTES_IN_HOUR
        val remainingMinutes = this % MINUTES_IN_HOUR

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

    companion object {
        const val MINUTES_IN_HOUR = 60
        const val DOT_DELIMETER = " ∙ "
        const val MULTIPOINT = "..."
        const val MAX_COUNTRY_NUMBER = 3
        const val GOOD_RATE_7 = 7.0f
    }
}
