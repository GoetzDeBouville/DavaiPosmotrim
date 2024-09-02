package com.davay.android.feature.selectmovie.presentation

import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.feature.selectmovie.domain.FilterDislikedMovieListUseCase
import com.davay.android.feature.selectmovie.domain.GetMovieListUseCase
import com.davay.android.feature.selectmovie.domain.GetMovieIdListSizeUseCase
import com.davay.android.feature.selectmovie.domain.SwipeMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectMovieViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieListUseCase,
    private val getMovieIdListSizeUseCase: GetMovieIdListSizeUseCase,
    private val filterDislikedMovieListUseCase: FilterDislikedMovieListUseCase,
    private val swipeMovieUseCase: SwipeMovieUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow<SelectMovieState>(SelectMovieState.Loading)
    val state = _state.asStateFlow()

    private var currentPosition = 0
    private var totalMovieIds = 0
    private val loadedMovies = mutableListOf<MovieDetails>()

    init {
        initializeMovieList()
    }

    private fun loadMovies(position: Int) {
        runSafelyUseCase(
            useCaseFlow = getMovieDetailsUseCase(position),
            onSuccess = { movieList ->
                loadedMovies.addAll(movieList)
                _state.update {
                    SelectMovieState.Content(movieList = loadedMovies)
                }
            },
            onFailure = { error ->
                _state.update { SelectMovieState.Error(mapErrorToUiState(error)) }
            }
        )
    }

    private fun initializeMovieList() {
        viewModelScope.launch {
            totalMovieIds = getMovieIdListSizeUseCase()
            loadMovies(currentPosition)
        }
    }

    /**
     * Метод вызывает подгрузку фильмов, если это необходимо и устанавливает значение для поля
     * isLike в таблице movieId, данные значения используются для фильтрации элементов и обнолвения
     * списка id элементов, которые потребутются для загрузки данных о фильмах
     */
    fun onMovieSwiped(position: Int, isLiked: Boolean) {
        currentPosition = position
        if (currentPosition + PRELOAD_SIZE >= loadedMovies.size && loadedMovies.size < totalMovieIds) {
            loadMovies(currentPosition)
        }
        viewModelScope.launch {
            swipeMovieUseCase(position, isLiked)
        }
    }

    fun listIsFinished() {
        _state.update { SelectMovieState.ListIsFinished(movieList = mutableListOf()) }
    }

    /**
     * Метод фильтрует список id фильмов по признаку isLiked = false. Метод должен запускаться
     * в случае когда юзер пролистал все фильмы и должен получить список фильмов которые были
     * свайпнуты влево
     */
    fun filterMovieIdList() {
        viewModelScope.launch {
            filterDislikedMovieListUseCase.invoke()
        }
        loadMovies(0)
    }

    private companion object {
        const val PRELOAD_SIZE = 10
    }
}
