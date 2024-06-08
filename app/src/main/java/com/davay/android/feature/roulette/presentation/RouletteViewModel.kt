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
                originalTitle = "gfdавgd",
                yearCountryRuntime = "2008, USA",
                posterUrl = "https://clck.ru/3BACfT"
            ),
            FilmRouletteModel(
                id = 2,
                title = "Название 2",
                mark = 7.0f,
                originalTitle = "gfdgdпв",
                yearCountryRuntime = "2008, USA",
                posterUrl = "https://ir.ozone.ru/s3/multimedia-j/c1000/6379140283.jpg"
            ),
            FilmRouletteModel(
                id = 3,
                title = "Название 3",
                mark = 9.4f,
                originalTitle = "gfdgd",
                yearCountryRuntime = "1991, UK",
                posterUrl = "https://static.insales-cdn.com/images/products/1/2985/388467625/%D0%BF0362.png"
            ),
            FilmRouletteModel(
                id = 4,
                title = "Название 4",
                mark = 5.0f,
                originalTitle = "gfавпdgd",
                yearCountryRuntime = "2018, USA",
                posterUrl = "https://clck.ru/3BACbb"
            ),
            FilmRouletteModel(
                id = 5,
                title = "Название 5",
                mark = 8.9f,
                originalTitle = "hjjgjkhk hjjh jhjhjk hjkjk",
                yearCountryRuntime = "2010, Canada",
                posterUrl = "https://clck.ru/3BACae"
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
