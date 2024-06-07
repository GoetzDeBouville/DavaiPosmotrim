package com.davay.android.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.davay.android.feature.selectmovie.MovieDetailsDemo
import com.google.android.flexbox.FlexboxLayout

interface MovieDetailsHelper {
    fun setImage(img: ImageView, url: String?)
    fun setRateText(tvRate: TextView, ratingKinopoisk: Float?, context: Context)
    fun addGenreList(flexBox: FlexboxLayout, genres: List<String>, context: Context)
    fun buildStringYearCountriesRuntime(data: MovieDetailsDemo, context: Context): String
}
