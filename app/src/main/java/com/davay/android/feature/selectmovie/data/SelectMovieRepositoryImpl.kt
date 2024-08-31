package com.davay.android.feature.selectmovie.data

import com.davay.android.core.data.converters.toDbEntity
import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.database.HistoryDao
import com.davay.android.core.data.database.MovieIdDao
import com.davay.android.core.data.network.HttpNetworkClient
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
    private val movieIdDao: MovieIdDao,
    private val historyDao: HistoryDao
) : SelectMovieRepository {
    /**
     * Метод принимает номер позиции и возвращает список MovieDetails
     */
    override fun getMovieByPositionId(positionNumber: Int): Flow<Result<List<MovieDetails>, ErrorType>> =
        flow {
            val movies = mutableListOf<MovieDetails>()

            val dbMovies = getMoviesFromDatabase(positionNumber, PAGINATION_SIZE)
            movies.addAll(dbMovies)

            if (dbMovies.size < PAGINATION_SIZE) {
                val missingMovies = getMissingMoviesFromNetwork(
                    positionNumber + dbMovies.size,
                    PAGINATION_SIZE - dbMovies.size
                )
                movies.addAll(missingMovies)
            }

            emit(Result.Success(movies))
        }

    private suspend fun getMoviesFromDatabase(positionNumber: Int, limit: Int): List<MovieDetails> {
        return withContext(Dispatchers.IO) {
            movieIdDao.getMovieIdsByPositionRange(positionNumber, limit).map { movieId ->
                historyDao.getMovieDetailsById(movieId).toDomain()
            }
        }
    }

    private suspend fun getMissingMoviesFromNetwork(startPosition: Int, limit: Int): List<MovieDetails> {
        val movies = mutableListOf<MovieDetails>()
        val movieIds = withContext(Dispatchers.IO) {
            movieIdDao.getMovieIdsByPositionRange(startPosition, limit)
        }

        for (movieId in movieIds) {
            val movieDetails = fetchMovieFromNetwork(movieId)
            if (movieDetails != null) {
                movies.add(movieDetails)
            }
        }

        return movies
    }

    private suspend fun fetchMovieFromNetwork(movieId: Int): MovieDetails? {
        val response = httpNetworkClient.getResponse(GetMovieRequest.Movie(movieId))
        return when (val body = response.body) {
            is GetMovieResponse.Movie -> {
                val movieDetails = body.value.toDomain()
                saveMovieToDatabase(movieDetails)
                movieDetails
            }
            else -> {
                null
            }
        }
    }

    private suspend fun saveMovieToDatabase(movieDetails: MovieDetails) {
        withContext(Dispatchers.IO) {
            historyDao.insertMovie(movieDetails.toDbEntity())
        }
    }

    override suspend fun getMovieIdListSize(): Int {
        return movieIdDao.getMovieIdsCount()
    }

    private companion object {
        val TAG = SelectMovieRepositoryImpl::class.simpleName
        const val PAGINATION_SIZE = 10
    }
}