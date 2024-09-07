package com.davay.android.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.davai.uikit.MovieEvaluationView
import com.davai.uikit.ProgressBarView
import com.davai.uikit.TagView
import com.davay.android.R
import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.feature.selectmovie.presentation.AdditionalInfoInflater
import com.google.android.flexbox.FlexboxLayout

/**
 * Добавляем класс для избегания дублирования логики
 */
open class MovieDetailsHelperImpl : MovieDetailsHelper, AdditionalInfoInflater {
    override fun setImage(img: ImageView, progressBar: ProgressBarView, url: String?) {
        if (url.isNullOrEmpty()) {
            img.load(com.davai.uikit.R.drawable.placeholder_error_332dp) {
                transformations(RoundedCornersTransformation())
                    .crossfade(true)
            }
        } else {
            img.load(url) {
                listener(
                    onStart = {
                        progressBar.isGone = false
                    },
                    onSuccess = { _, result ->
                        progressBar.isGone = true
                        img.setImageDrawable(result.drawable)
                    },
                    onError = { _, _ ->
                        progressBar.isGone = true
                        img.setImageResource(com.davai.uikit.R.drawable.placeholder_error_332dp)
                    }
                ).scale(Scale.FIT)
                transformations(
                    RoundedCornersTransformation()
                ).crossfade(true)
            }
        }
    }

    override fun setRateText(tvRate: TextView, ratingKinopoisk: Float?) {
        ratingKinopoisk?.let {
            if (ratingKinopoisk > 1) { // Убираем оценку если ее нет. Минимальная оценка на кинопоиск это 1
                val textColor = if (ratingKinopoisk >= GOOD_RATE_7) {
                    tvRate.context.getColor(com.davai.uikit.R.color.done)
                } else {
                    tvRate.context.getColor(com.davai.uikit.R.color.attention)
                }
                tvRate.apply {
                    text = ratingKinopoisk.toString()
                    setTextColor(textColor)
                }
            } else {
                tvRate.text = ""
            }
        }
    }

    override fun addGenreList(flexBox: FlexboxLayout, genres: List<String>) {
        flexBox.removeAllViews()
        genres.forEach { genre ->
            val tagView = createTagView(genre, flexBox.context)
            flexBox.addView(tagView)
        }
    }

    override fun buildStringYearCountriesRuntime(data: MovieDetails, context: Context): String {
        val str = StringBuilder()
        appendYear(data.year, str)
        appendCountries(data.countries, str)
        appendDuration(data.duration, str, context)
        return str.toString()
    }

    private fun appendYear(year: String?, str: StringBuilder) {
        year?.let {
            str.append(it)
            str.append(DOT_DELIMETER)
        }
    }

    override fun inflateCastList(fbl: FlexboxLayout, list: List<String>) {
        fbl.removeAllViews()
        val castList = list.take(MAX_CAST_NUMBER_4)
        castList.forEach {
            val castView = LayoutInflater.from(fbl.context).inflate(
                R.layout.item_top_cast,
                fbl,
                false
            ) as TextView
            castView.text = it
            fbl.addView(castView)
        }
    }

    override fun setRate(rateNum: Float?, numberOfRates: Int?, view: MovieEvaluationView) {
        if (rateNum == null) {
            view.visibility = View.GONE
        } else {
            view.apply {
                setRateNum(rateNum)
                setNumberOfRatesString(numberOfRates!!)
            }
        }
    }

    private fun appendCountries(countries: List<String>?, str: StringBuilder) {
        countries?.let {
            if (it.isNotEmpty()) {
                val countryList = it.take(MAX_COUNTRY_NUMBER)
                val countriesString = countryList.joinToString(separator = DOT_DELIMETER)
                str.append(countriesString)
                if (it.size > MAX_COUNTRY_NUMBER) {
                    str.append(MULTIPOINT)
                }
            }
        }
    }

    private fun appendDuration(duration: Int?, str: StringBuilder, context: Context) {
        duration?.let {
            str.append(DOT_DELIMETER)
            str.append(it.formatMovieDuration(context))
        }
    }

    private fun Int.formatMovieDuration(context: Context): String {
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

    private fun createTagView(text: String, context: Context): TagView {
        val tagView = TagView(context).apply {
            setText(text)
            changeStyle(TagView.Companion.Style.PRIMARY_VIOLET)
        }

        val layoutParams = FlexboxLayout.LayoutParams(
            FlexboxLayout.LayoutParams.WRAP_CONTENT,
            FlexboxLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            val marginEnd =
                context.resources.getDimensionPixelSize(com.davai.uikit.R.dimen.margin_8)
            val marginBottom =
                context.resources.getDimensionPixelSize(com.davai.uikit.R.dimen.margin_12)
            setMargins(0, 0, marginEnd, marginBottom)
        }

        tagView.layoutParams = layoutParams
        return tagView
    }

    private companion object {
        const val MINUTES_IN_HOUR = 60
        const val DOT_DELIMETER = " ∙ "
        const val MULTIPOINT = "..."
        const val MAX_COUNTRY_NUMBER = 3
        const val GOOD_RATE_7 = 7.0f
        const val MAX_CAST_NUMBER_4 = 4
    }
}
