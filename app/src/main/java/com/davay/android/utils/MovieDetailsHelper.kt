package com.davay.android.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.davay.android.domain.models.MovieDetails
import com.google.android.flexbox.FlexboxLayout

interface MovieDetailsHelper {
    fun setImage(img: ImageView, url: String?)
    fun setRateText(tvRate: TextView, ratingKinopoisk: Float?)
    fun addGenreList(flexBox: FlexboxLayout, genres: List<String>)
    fun buildStringYearCountriesRuntime(data: MovieDetails, context: Context): String
}
