package com.davay.android.feature.selectmovie.presentation

import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.models.ErrorScreenState
import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.feature.selectmovie.domain.FilterDislikedMovieListUseCase
import com.davay.android.feature.selectmovie.domain.GetMovieIdListSizeUseCase
import com.davay.android.feature.selectmovie.domain.GetMovieListUseCase
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

    private var totalMovieIds = 0
    private var loadedMovies = mutableSetOf<MovieDetails>()

    init {
        initializeMovieList()
    }

    private fun loadMovies(position: Int) {
        runSafelyUseCase(
            useCaseFlow = getMovieDetailsUseCase(position),
            onSuccess = { movieList ->
                if (movieList.isEmpty()) {
                    _state.update {
                        SelectMovieState.Error(ErrorScreenState.MOVIE_LIST_FINISHED)
                    }
                } else {
                    loadedMovies =
                        (state.value as? SelectMovieState.Content)?.movieList ?: mutableSetOf()
                    loadedMovies.addAll(movieList)
                    _state.update {
                        SelectMovieState.Content(movieList = loadedMovies)
                    }
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
            loadMovies(0)
        }
    }

    /**
     * Метод вызывает подгрузку фильмов, если это необходимо и устанавливает значение для поля
     * isLike в таблице movieId, данные значения используются для фильтрации элементов и обнолвения
     * списка id элементов, которые потребутются для загрузки данных о фильмах
     */
    fun onMovieSwiped(position: Int, isLiked: Boolean) {
        if (position + PRELOAD_SIZE >= loadedMovies.size && loadedMovies.size < totalMovieIds) {
            loadMovies(position)
        }
        viewModelScope.launch {
            swipeMovieUseCase(position, isLiked)
        }

        if (position == totalMovieIds) {
            _state.update { SelectMovieState.ListIsFinished }
        }
    }

    /**
     * Метод фильтрует список id фильмов по признаку isLiked = false. Метод должен запускаться
     * в случае когда юзер пролистал все фильмы и должен получить список фильмов которые были
     * свайпнуты влево
     */
    fun filterDislikedMovieList() {
        loadedMovies = mutableSetOf()
        _state.update {
            SelectMovieState.Content(movieList = loadedMovies)
        }

        viewModelScope.launch {
            filterDislikedMovieListUseCase()
            initializeMovieList()
        }
    }

    private companion object {
        /**
         * Размер подгрузки фильмов, при изменении так же учитывать значение в SelectMovieRepositoryImpl
         */
        const val PRELOAD_SIZE = 20
    }
}
