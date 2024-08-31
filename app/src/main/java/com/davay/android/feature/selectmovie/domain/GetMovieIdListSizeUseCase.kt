package com.davay.android.feature.selectmovie.domain

import com.davay.android.feature.selectmovie.domain.api.SelectMovieRepository
import javax.inject.Inject

class GetMovieIdListSizeUseCase @Inject constructor(
    private val repository: SelectMovieRepository
) {
    suspend operator fun invoke(): Int {
        return repository.getMovieIdListSize()
    }
}