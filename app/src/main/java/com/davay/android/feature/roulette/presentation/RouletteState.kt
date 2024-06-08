package com.davay.android.feature.roulette.presentation

import com.davay.android.feature.roulette.presentation.model.FilmRouletteModel
import com.davay.android.feature.roulette.presentation.model.UserRouletteModel

sealed class RouletteState {
    data class Init(
        val users: List<UserRouletteModel>,
        val films: List<FilmRouletteModel>
    ) : RouletteState()

    data class Waiting(
        val users: List<UserRouletteModel>
    ) : RouletteState()

    data class Roulette(val index: Int, val count: Int) : RouletteState()
    data class Match(val film: FilmRouletteModel) : RouletteState()
    data object Error : RouletteState()
}
