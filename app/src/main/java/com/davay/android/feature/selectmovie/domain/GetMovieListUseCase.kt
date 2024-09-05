package com.davay.android.feature.selectmovie.domain

import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.core.domain.models.Result
import com.davay.android.feature.selectmovie.domain.api.SelectMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(
    private val repository: SelectMovieRepository
) {
    operator fun invoke(positionNumber: Int): Flow<Result<List<MovieDetails>, ErrorType>> {
        return repository.getMovieListByPositionId(positionNumber)
    }
}