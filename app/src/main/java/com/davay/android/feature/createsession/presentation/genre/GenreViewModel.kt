package com.davay.android.feature.createsession.presentation.genre

import android.util.Log
import com.davay.android.base.BaseViewModel
import com.davay.android.feature.createsession.domain.model.GenreSelect
import javax.inject.Inject

class GenreViewModel @Inject constructor() : BaseViewModel() {

    private val selectedGenreList = mutableListOf<GenreSelect>()

    fun genreClicked(genre: GenreSelect) {
        if (genre.isSelected) {
            selectedGenreList.add(genre)
        } else {
            selectedGenreList.remove(genre)
        }
    }

    fun buttonContinueClicked() {
        Log.d("MyTag", selectedGenreList.toString())
    }

    // Метод для проверки, выбран ли хотя бы один жанр
    fun hasSelectedGenres(): Boolean {
        return selectedGenreList.isNotEmpty()
    }
}
