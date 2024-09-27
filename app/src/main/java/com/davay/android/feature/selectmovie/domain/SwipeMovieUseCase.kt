package com.davay.android.feature.selectmovie.domain

import com.davay.android.feature.selectmovie.domain.api.SelectMovieRepository
import javax.inject.Inject

class SwipeMovieUseCase @Inject constructor(
    private val repository: SelectMovieRepository
) {
    suspend operator fun invoke(position: Int, isLiked: Boolean) {
        return repository.updateIsLikedByPosition(position, isLiked)
    }
}