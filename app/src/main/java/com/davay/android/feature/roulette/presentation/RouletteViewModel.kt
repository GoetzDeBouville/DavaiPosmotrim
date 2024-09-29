package com.davay.android.feature.roulette.presentation

import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import com.davay.android.BuildConfig
import com.davay.android.R
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.impl.CommonWebsocketInteractor
import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.feature.roulette.domain.impl.RouletteMoviesUseCase
import com.davay.android.feature.roulette.domain.impl.StartRouletteUseCase
import com.davay.android.feature.roulette.presentation.model.UserRouletteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class RouletteViewModel @Inject constructor(
    private val commonWebsocketInteractor: CommonWebsocketInteractor,
    private val startRouletteUseCase: StartRouletteUseCase,
    private val rouletteMoviesUseCase: RouletteMoviesUseCase,
) : BaseViewModel() {

    private val _state: MutableStateFlow<RouletteState> = MutableStateFlow(
        RouletteState.Init(emptyList(), emptyList())
    )
    val state: StateFlow<RouletteState>
        get() = _state

    private var users: List<UserRouletteModel> = emptyList()
    private var films: List<MovieDetails> = emptyList()
    private var watchFilmId: Int = 0
    private val sessionId: String = commonWebsocketInteractor.sessionId.toString()

    init {
        subscribeToWs()
//        _state = MutableStateFlow(RouletteState.Init(users, films))
//        if (films.any { it.id == watchFilmId }) {
//            checkUsers()
//        } else {
//            _state.value = RouletteState.Error
//        }
    }

    private fun subscribeToWs() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.getSessionResult()
                    .collect { result ->
                        result?.fold(
                            onSuccess = { session ->
                                launch {
                                    rouletteMoviesUseCase.getMoviesByIdList(idList = session.matchedMovieIdList)
                                        .collect { moviesListResult ->
                                            moviesListResult.fold(
                                                onSuccess = { movies ->
                                                    val userList =
                                                        session.users.mapIndexed { index, user ->
                                                            UserRouletteModel(
                                                                id = index,
                                                                name = user,
                                                                isConnected = false
                                                            )
                                                        }
                                                    users = userList
                                                    films = movies
                                                    _state.update {
                                                        RouletteState.Waiting(
                                                            users = userList,
                                                            films = movies
                                                        )
                                                    }
                                                },
                                                onError = {
                                                    _state.update { RouletteState.Error }
                                                }
                                            )
                                        }
                                }
                            },
                            onError = {
                                _state.update { RouletteState.Error }
                            }
                        )
                    }
            }.onFailure { error ->
                _state.update { RouletteState.Error }
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                commonWebsocketInteractor.getRouletteId()
                    .collect { result ->
                        result?.fold(
                            onSuccess = { filmId ->
                                watchFilmId = filmId
                            },
                            onError = {
                                _state.update { RouletteState.Error }
                            }
                        )
                    }
            }.onFailure { error ->
                _state.update { RouletteState.Error }
                if (BuildConfig.DEBUG) {
                    error.printStackTrace()
                }
            }
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
        runSafelyUseCase(
            useCaseFlow = startRouletteUseCase.startRoulette(sessionId),
            onSuccess = {},
            onFailure = { _state.update { RouletteState.Error } }
        )
    }

    fun navigateToMainFragment() {
        navigate(R.id.action_rouletteFragment_to_mainFragment)
    }

    fun navigateToSessionHistory() {
        navigate(
            navDirections = R.id.action_rouletteFragment_to_matchedSessionFragment2,
            bundle = bundleOf(SESSION_ID to sessionId)
        )
    }

    companion object {
        private const val DELAY_TIME_MS_1000 = 1000L
        private const val TEMP_NUMBER_6 = 6
        private const val TEMP_NUMBER_3 = 3
        const val SESSION_ID = "session_id"
    }
}
