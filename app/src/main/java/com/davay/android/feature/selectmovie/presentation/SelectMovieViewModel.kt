package com.davay.android.feature.selectmovie.presentation

import androidx.lifecycle.viewModelScope
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.feature.selectmovie.domain.GetMovieDetailsUseCase
import com.davay.android.feature.selectmovie.domain.GetMovieIdListSizeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectMovieViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getMovieIdListSizeUseCase: GetMovieIdListSizeUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow<SelectMovieState>(SelectMovieState.Loading)
    val state = _state.asStateFlow()

    private var currentPosition = 0
    private var totalMovieIds = 0
    private val loadedMovies = mutableListOf<MovieDetails>()

    init {
        initializeMovieList()
    }

    private fun loadMovies(startPosition: Int) {
        val endPosition = minOf(startPosition + PRELOAD_SIZE, totalMovieIds)
        for (position in startPosition until endPosition) {
            loadSingleMovie(position)
        }
    }

    private fun loadSingleMovie(position: Int) {
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

    fun onMovieSwiped(position: Int) {
        currentPosition = position
        if (currentPosition + PRELOAD_SIZE >= loadedMovies.size && loadedMovies.size < totalMovieIds) {
            loadMovies(currentPosition)
        }
    }

    fun listIsFinished() {
        _state.update { SelectMovieState.ListIsFinished(movieList = mutableListOf()) }
    }

    private companion object {
        const val PRELOAD_SIZE = 10
    }
}
