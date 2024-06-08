package com.davay.android.feature.roulette.presentation.model

data class FilmRouletteModel(
    val id: Int,
    val title: String,
    val mark: Float,
    val originalTitle: String,
    val yearCountryRuntime: String,
    val posterUrl: String,
) {
    companion object {
        const val LOW_MARK_BORDER_5 = 5
        const val HIGH_MARK_BORDER_7 = 7
    }
}
