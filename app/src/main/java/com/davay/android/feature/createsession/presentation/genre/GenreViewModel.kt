package com.davay.android.feature.createsession.presentation.genre

import android.util.Log
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.models.ErrorScreenState
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Genre
import com.davay.android.feature.createsession.domain.model.GenreSelect
import com.davay.android.feature.createsession.domain.usecase.GetGenresUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class GenreViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow<GenreState>(GenreState.Loading)
    val state = _state.asStateFlow()

    private val selectedGenre = mutableListOf<GenreSelect>()

    init {
        getGenreList()
    }

    fun getGenreList() {
        _state.update { GenreState.Loading }
        runSafelyUseCase(
            useCaseFlow = getGenresUseCase.execute(),
            onSuccess = { genres ->
                if (genres.isEmpty()) {
                    _state.update { GenreState.Error(ErrorScreenState.EMPTY) }
                } else {
                    val uiGenres = genres.map { it.toGenre() }
                    _state.update { GenreState.Content(uiGenres) }
                }
            },
            onFailure = { error ->
                _state.update { GenreState.Error(renderError(error)) }
            }
        )
    }

    private fun renderError(errorType: ErrorType): ErrorScreenState {
        return when (errorType) {
            ErrorType.NO_CONNECTION -> ErrorScreenState.NO_INTERNET
            ErrorType.NOT_FOUND -> ErrorScreenState.SERVER_ERROR
            ErrorType.BAD_REQUEST -> ErrorScreenState.SERVER_ERROR
            ErrorType.APP_VERSION_ERROR -> ErrorScreenState.APP_VERSION_ERROR
            else -> ErrorScreenState.SERVER_ERROR
        }
    }

    fun genreClicked(genre: GenreSelect) {
        if (genre.isSelected) {
            selectedGenre.add(genre)
        } else {
            selectedGenre.remove(genre)
        }
    }

    fun buttonContinueClicked() {
        Log.d(TAG, selectedGenre.toString())
    }

    private fun Genre.toGenre() = GenreSelect(
        name = this.name,
        isSelected = false
    )

    // Метод для проверки, выбран ли хотя бы один жанр
    fun hasSelectedGenres(): Boolean {
        return selectedGenre.isNotEmpty()
    }

    private companion object {
        val TAG = GenreViewModel::class.simpleName
    }
}
