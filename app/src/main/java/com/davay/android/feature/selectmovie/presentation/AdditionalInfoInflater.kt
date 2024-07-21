package com.davay.android.feature.selectmovie.presentation

import com.davai.uikit.MovieEvaluationView
import com.google.android.flexbox.FlexboxLayout

interface AdditionalInfoInflater {
    fun inflateCastList(fbl: FlexboxLayout, list: List<String>)
    fun setRate(rateNum: Float?, numberOfRates: Int?, view: MovieEvaluationView)
}