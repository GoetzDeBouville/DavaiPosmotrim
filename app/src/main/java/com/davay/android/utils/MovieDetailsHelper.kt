package com.davay.android.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.davai.uikit.ProgressBarView
import com.davay.android.core.domain.models.MovieDetails
import com.google.android.flexbox.FlexboxLayout

interface MovieDetailsHelper {
    fun setImage(img: ImageView, progressBar: ProgressBarView, url: String?)
    fun setRateText(tvRate: TextView, ratingKinopoisk: Float?)
    fun addGenreList(flexBox: FlexboxLayout, genres: List<String>)
    fun buildStringYearCountriesRuntime(data: MovieDetails, context: Context): String
}
