package com.davay.android.feature.createsession.presentation

import com.davay.android.base.BaseViewModel
import com.davay.android.feature.createsession.domain.model.Genre
import javax.inject.Inject

class GenreViewModel @Inject constructor() : BaseViewModel() {

    private val selectedGenreList = mutableListOf<Genre>()

    fun genreClicked(genre: Genre) {
        if (genre.isSelected) {
            selectedGenreList.add(genre)
        } else {
            selectedGenreList.remove(genre)
        }
    }
}
