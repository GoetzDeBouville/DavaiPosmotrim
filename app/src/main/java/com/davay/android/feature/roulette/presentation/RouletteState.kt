package com.davay.android.feature.roulette.presentation

import com.davay.android.feature.roulette.presentation.model.UserRouletteModel
import com.davay.android.feature.selectmovie.domain.models.MovieDetailsDemo

sealed class RouletteState {
    data class Init(
        val users: List<UserRouletteModel>,
        val films: List<MovieDetailsDemo>
    ) : RouletteState()

    data class Waiting(
        val users: List<UserRouletteModel>
    ) : RouletteState()

    data class Roulette(val index: Int, val count: Int) : RouletteState()
    data class Match(val film: MovieDetailsDemo) : RouletteState()
    data object Error : RouletteState()
}
