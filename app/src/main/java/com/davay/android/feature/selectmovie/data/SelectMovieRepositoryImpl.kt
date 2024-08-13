package com.davay.android.feature.selectmovie.data

import android.util.Log
import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.database.MovieIdDao
import com.davay.android.core.data.network.HttpNetworkClient
import com.davay.android.core.data.network.model.mapToErrorType
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.core.domain.models.Result
import com.davay.android.feature.selectmovie.data.network.GetMovieRequest
import com.davay.android.feature.selectmovie.data.network.GetMovieResponse
import com.davay.android.feature.selectmovie.domain.api.SelectMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SelectMovieRepositoryImpl @Inject constructor(
    private val httpNetworkClient: HttpNetworkClient<GetMovieRequest, GetMovieResponse>,
    private val movieIdDao: MovieIdDao
) : SelectMovieRepository {
    /**
     * Метод принимает номер позиции и возвращает
     */
    override fun getMovieByPositionId(positionNumber: Int): Flow<Result<MovieDetails, ErrorType>> =
        flow {
            val movieId = withContext(Dispatchers.IO) {
                movieIdDao.getMovieIdByPosition(positionNumber)?.movieId ?: 0
            }
            if (movieId == 0) {
                Log.e(TAG, "Illegal movie position")
                emit(Result.Error(ErrorType.NOT_FOUND))
            } else {
                val response = httpNetworkClient.getResponse(GetMovieRequest.Movie(movieId))
                when (val body = response.body) {
                    is GetMovieResponse.Movie -> {
                        emit(Result.Success(body.value.toDomain()))
                    }
                    else -> {
                        emit(Result.Error(response.resultCode.mapToErrorType()))
                    }
                }
            }
        }

    private companion object {
        val TAG = SelectMovieRepositoryImpl::class.simpleName
    }
}