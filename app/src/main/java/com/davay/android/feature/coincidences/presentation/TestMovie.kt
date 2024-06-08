package com.davay.android.feature.coincidences.presentation

import androidx.annotation.StringRes

data class TestMovie(
    val id: Int,
    val title: String,
    @StringRes
    val imageUrl: Int
)
