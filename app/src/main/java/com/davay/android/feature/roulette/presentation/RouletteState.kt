package com.davay.android.feature.roulette.presentation

import com.davay.android.feature.roulette.presentation.model.UserRouletteModel
import com.davay.android.feature.selectmovie.domain.models.MovieDetailsDemo

sealed interface RouletteState {
    class Init(
        val users: List<UserRouletteModel>,
        val films: List<MovieDetailsDemo>
    ) : RouletteState

    class Waiting(
        val users: List<UserRouletteModel>
    ) : RouletteState

    class Roulette(val index: Int, val count: Int) : RouletteState
    data class Match constructor(val film: MovieDetailsDemo) : RouletteState
    object Error : RouletteState
}
