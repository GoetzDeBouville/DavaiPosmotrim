package com.davay.android.feature.createsession.data

import android.database.sqlite.SQLiteException
import android.util.Log
import com.davay.android.BuildConfig
import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.database.MovieIdDao
import com.davay.android.core.data.database.entity.MovieIdEntity
import com.davay.android.core.data.network.HttpNetworkClient
import com.davay.android.core.data.network.model.mapToErrorType
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.models.CompilationFilms
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Genre
import com.davay.android.core.domain.models.Result
import com.davay.android.core.domain.models.Session
import com.davay.android.feature.createsession.data.network.CreateSessionRequest
import com.davay.android.feature.createsession.data.network.CreateSessionResponse
import com.davay.android.feature.createsession.domain.api.CreateSessionRepository
import com.davay.android.feature.createsession.domain.model.SessionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateSessionRepositoryImpl @Inject constructor(
    private val httpNetworkClient: HttpNetworkClient<CreateSessionRequest, CreateSessionResponse>,
    private val userDataRepository: UserDataRepository,
    private val movieIdDao: MovieIdDao
) : CreateSessionRepository {
    override fun getCollections(): Flow<Result<List<CompilationFilms>, ErrorType>> = flow {
        val response = httpNetworkClient.getResponse(CreateSessionRequest.CollectionList)
        when (val body = response.body) {
            is CreateSessionResponse.CollectionList -> {
                emit(Result.Success(body.value.map { it.toDomain() }))
            }

            else -> {
                emit(Result.Error(response.resultCode.mapToErrorType()))
            }
        }
    }

    override fun getGenres(): Flow<Result<List<Genre>, ErrorType>> = flow {
        val response = httpNetworkClient.getResponse(CreateSessionRequest.GenreList)
        when (val body = response.body) {
            is CreateSessionResponse.GenreList -> {
                emit(Result.Success(body.value.map { it.toDomain() }))
            }

            else -> {
                emit(Result.Error(response.resultCode.mapToErrorType()))
            }
        }
    }

    override fun createSession(
        sessionType: SessionType,
        requestBody: List<String>
    ): Flow<Result<Session, ErrorType>> = flow {
        val userId = userDataRepository.getUserId()
        val sessionParameter = if (sessionType == SessionType.GENRES) {
            GENRES
        } else {
            COLLECTIONS
        }
        val response = httpNetworkClient.getResponse(
            CreateSessionRequest.Session(
                parameter = sessionParameter,
                requestBody = requestBody,
                userId = userId
            )
        )
        when (val body = response.body) {
            is CreateSessionResponse.Session -> {
                saveMovieIdListToDb(body.value.movieIdList)
                emit(Result.Success(body.value.toDomain()))
            }

            else -> {
                emit(Result.Error(response.resultCode.mapToErrorType()))
            }
        }
    }

    /**
     * Метод производит очистку таблицы с id с последующей записью в нее списка обновленных id
     */
    private suspend fun saveMovieIdListToDb(idList: List<Int>) {
        try {
            clearAndResetIdsTable()

            idList.forEach { id ->
                insertMovieToDb(id)
            }
        } catch (e: SQLiteException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "Database operation failed: ${e.localizedMessage}", e)
            }
        }
    }

    private suspend fun insertMovieToDb(movieId: Int) {
        try {
            movieIdDao.insertMovieId(MovieIdEntity(movieId = movieId))
        } catch (e: SQLiteException) {
            if (BuildConfig.DEBUG) {
                Log.e(
                    TAG,
                    "Error inserting movie ID: $movieId, exception -> ${e.localizedMessage}"
                )
            }
        }
    }

    private suspend fun clearAndResetIdsTable() {
        val movieIdCount = try {
            movieIdDao.getMovieIdsCount()
        } catch (e: SQLiteException) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "Error in getMovieIdsCount, exception -> ${e.localizedMessage}")
            }
            0
        }

        if (movieIdCount > 0) {
            try {
                movieIdDao.clearAndResetTable()
            } catch (e: SQLiteException) {
                if (BuildConfig.DEBUG) {
                    Log.e(
                        TAG,
                        "Error in clear and reset table, exception -> ${e.localizedMessage}"
                    )
                }
            }
        }
    }

    private companion object {
        val TAG = CreateSessionRepositoryImpl::class.simpleName
        const val COLLECTIONS = "collections"
        const val GENRES = "genres"
    }
}