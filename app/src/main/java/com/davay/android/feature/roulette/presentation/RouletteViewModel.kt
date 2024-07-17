package com.davay.android.feature.roulette.presentation

import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.domain.models.MovieDetails
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
    private val films: List<MovieDetails>
    private val watchFilmId: Int

    init {
        users = listOf(
            UserRouletteModel(1, "Masha"),
            UserRouletteModel(2, "Sasha"),
            UserRouletteModel(TEMP_NUMBER_3, "Sasha Sasha Sasha Sasha Sasha")
        )

        films = listOf(
            MovieDetails(
                id = 1,
                name = "Название 1",
                description = null,
                year = "2008",
                countries = listOf(),
                imgUrl = "https://clck.ru/3BACfT",
                alternativeName = "Rodger Dawson",
                ratingKinopoisk = 2.0f,
                ratingImdb = null,
                numOfMarksKinopoisk = null,
                numOfMarksImdb = null,
                duration = 90,
                genres = listOf(),
                directors = listOf(),
                actors = listOf()
            ),
            MovieDetails(
                id = 1,
                name = "Название 2",
                description = null,
                year = "2001",
                countries = listOf(),
                imgUrl = "https://ir.ozone.ru/s3/multimedia-j/c1000/6379140283.jpg",
                alternativeName = "Alt name",
                ratingKinopoisk = 7.1f,
                ratingImdb = null,
                numOfMarksKinopoisk = null,
                numOfMarksImdb = null,
                duration = 101,
                genres = listOf(),
                directors = listOf(),
                actors = listOf()
            ),

            MovieDetails(
                id = 3,
                name = "Название 3",
                description = null,
                year = "1989",
                countries = listOf(),
                imgUrl = "https://static.insales-cdn.com/images/products/1/2985/388467625/%D0%BF0362.png",
                alternativeName = "Justin Madden",
                ratingKinopoisk = 9.4f,
                ratingImdb = null,
                numOfMarksKinopoisk = null,
                numOfMarksImdb = null,
                duration = 122,
                genres = listOf(),
                directors = listOf(),
                actors = listOf()
            ),
            MovieDetails(
                id = 4,
                name = "Название 4",
                description = null,
                year = null,
                countries = listOf(),
                imgUrl = "https://clck.ru/3BACbb",
                alternativeName = "Juanita Cabrera",
                ratingKinopoisk = 5f,
                ratingImdb = null,
                numOfMarksKinopoisk = null,
                numOfMarksImdb = null,
                duration = null,
                genres = listOf(),
                directors = listOf(),
                actors = listOf()
            ),
            MovieDetails(
                id = 5,
                name = "Название 5",
                description = null,
                year = "2010",
                countries = listOf(),
                imgUrl = "https://clck.ru/3BACae",
                alternativeName = "Lowell Whitney",
                ratingKinopoisk = null,
                ratingImdb = null,
                numOfMarksKinopoisk = null,
                numOfMarksImdb = null,
                duration = 176,
                genres = listOf(),
                directors = listOf(),
                actors = listOf()
            ),
        )

        _state = MutableStateFlow(RouletteState.Init(users, films))
        watchFilmId = Random.nextInt(1, TEMP_NUMBER_6)
        if (films.any { it.id == watchFilmId }) {
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
                    _state.value = RouletteState.Waiting(users = users, films = films)
                    if (users.all { it.isConnected }) {
                        delay(DELAY_TIME_MS_1000)
                        val index = films.indexOfFirst { it.id == watchFilmId }
                        _state.value = RouletteState.Roulette(
                            index = index,
                            count = films.size,
                            users = users,
                            films = films
                        )
                    }
                }
            }
        }
    }

    fun rouletteScrollingStopped() {
        viewModelScope.launch {
            delay(DELAY_TIME_MS_1000)
            _state.value =
                films.find { it.id == watchFilmId }?.let { RouletteState.Match(it) }
                    ?: RouletteState.Error
        }
    }

    private fun tempFun(): Flow<List<UserRouletteModel>?> = flow {
        for (i in 0..2) {
            delay(DELAY_TIME_MS_1000 * TEMP_NUMBER_6)
            val list = mutableListOf<UserRouletteModel>()
            list.addAll(users.map { it.copy() })
            list[i].isConnected = true
            emit(list)
        }
    }

    fun rouletteStart() {
        // отправляем запрос на добавление других пользователей в рулетку
    }

    companion object {
        private const val DELAY_TIME_MS_1000 = 1000L
        private const val TEMP_NUMBER_6 = 6
        private const val TEMP_NUMBER_3 = 3
    }
}
