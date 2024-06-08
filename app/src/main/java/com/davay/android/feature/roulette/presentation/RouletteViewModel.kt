package com.davay.android.feature.roulette.presentation

import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.feature.roulette.presentation.model.FilmRouletteModel
import com.davay.android.feature.roulette.presentation.model.UserRouletteModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class RouletteViewModel @Inject constructor() : BaseViewModel() {

    private val _state: MutableStateFlow<RouletteState>
    val state: StateFlow<RouletteState>
        get() = _state

    private var users: List<UserRouletteModel>
    private val films: List<FilmRouletteModel>
    private val lookFilmId: Int

    init {
        users = listOf(
            UserRouletteModel(1, "Masha"),
            UserRouletteModel(2, "Sasha"),
            UserRouletteModel(TEMP_NUMBER_3, "Sasha Sasha Sasha")
        )
        films = listOf(
            FilmRouletteModel(
                id = 1,
                title = "Название 1",
                mark = 2.0f,
                originalTitle = "gfdgd",
                yearCountryRuntime = "2008, USA",
                posterUrl = "https://atthemovies.uk/cdn/shop/files/" +
                        "front_07977eff-c3c7-4aa6-a57d-5d3e34727031.png?v=1717112425"
            ),
            FilmRouletteModel(
                id = 2,
                title = "Название 2",
                mark = 7.0f,
                originalTitle = "gfdgd",
                yearCountryRuntime = "2008, USA",
                posterUrl = "https://atthemovies.uk/cdn/shop/files/" +
                        "back_ff1004f8-11e7-4e2b-81de-a9cd2a3de2c0.png?v=1717112172"
            ),
            FilmRouletteModel(
                id = 3,
                title = "Название 3",
                mark = 9.4f,
                originalTitle = "gfdgd",
                yearCountryRuntime = "1991, UK",
                posterUrl = "https://atthemovies.uk/cdn/shop/products/" +
                        "Gladiator2000us27x40in195u.jpg?v=1621385091"
            ),
            FilmRouletteModel(
                id = 4,
                title = "Название 4",
                mark = 5.0f,
                originalTitle = "gfdgd",
                yearCountryRuntime = "2018, USA",
                posterUrl = "https://atthemovies.uk/cdn/shop/files/" +
                        "front_efd356d9-ab5b-4dc3-a37f-452027a2b68d.png?v=1717110841"
            ),
            FilmRouletteModel(
                id = 5,
                title = "Название 5",
                mark = 8.9f,
                originalTitle = "hjjgjkhk hjjh jhjhjk hjkjk",
                yearCountryRuntime = "2010, Canada",
                posterUrl = "https://atthemovies.uk/cdn/shop/products/" +
                        "Template_cb39750d-7a4b-44ec-950d-172f4c2fe96c.jpg?v=1666078856"
            ),
        )

        _state = MutableStateFlow(RouletteState.Init(users, films))
        lookFilmId = Random.nextInt(1, TEMP_NUMBER_6)
        if (films.any { it.id == lookFilmId }) {
            checkUsers()
        } else {
            _state.value = RouletteState.Error
        }
    }

    private fun checkUsers() {
        viewModelScope.launch {
            tempFun().collect { list ->
                if (list.isNullOrEmpty()) {
                    _state.value = RouletteState.Error
                } else {
                    users = list
                    _state.value = RouletteState.Waiting(users)
                    if (users.all { it.isConnected }) {
                        delay(DELAY_TIME_MS_1000)
                        val index = films.indexOfFirst { it.id == lookFilmId }
                        _state.value = RouletteState.Roulette(index, films.size)
                    }
                }
            }
        }
    }

    fun rouletteScrollingStopped() {
        _state.value =
            films.find { it.id == lookFilmId }?.let { RouletteState.Match(it) }
                ?: RouletteState.Error
    }

    private fun tempFun(): Flow<List<UserRouletteModel>?> = flow {
        for (i in 0..2) {
            delay(DELAY_TIME_MS_1000)
            val list = mutableListOf<UserRouletteModel>()
            list.addAll(users.map { it.copy() })
            list[i].isConnected = true
            emit(list)
        }
    }

    companion object {
        private const val DELAY_TIME_MS_1000 = 1000L
        private const val TEMP_NUMBER_6 = 6
        private const val TEMP_NUMBER_3 = 3
    }
}
