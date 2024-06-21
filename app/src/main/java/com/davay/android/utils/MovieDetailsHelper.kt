package com.davay.android.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.davay.android.feature.selectmovie.domain.models.MovieDetailsDemo
import com.google.android.flexbox.FlexboxLayout

interface MovieDetailsHelper {
    fun setImage(img: ImageView, url: String?)
    fun setRateText(tvRate: TextView, ratingKinopoisk: Float?)
    fun addGenreList(flexBox: FlexboxLayout, genres: List<String>)
    fun buildStringYearCountriesRuntime(data: MovieDetailsDemo, context: Context): String
}
