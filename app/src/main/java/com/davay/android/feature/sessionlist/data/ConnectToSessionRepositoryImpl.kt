package com.davay.android.feature.sessionlist.data

import android.database.sqlite.SQLiteException
import android.util.Log
import com.davay.android.BuildConfig
import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.database.MovieIdDao
import com.davay.android.core.data.database.entity.MovieIdEntity
import com.davay.android.core.data.network.HttpNetworkClient
import com.davay.android.core.data.network.model.mapToErrorType
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.core.domain.models.Session
import com.davay.android.feature.sessionlist.data.network.ConnectToSessionRequest
import com.davay.android.feature.sessionlist.data.network.ConnectToSessionResponse
import com.davay.android.feature.sessionlist.domain.api.ConnectToSessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConnectToSessionRepositoryImpl @Inject constructor(
    private val movieIdDao: MovieIdDao,
    private val userDataRepository: UserDataRepository,
    private val httpNetworkClient: HttpNetworkClient<ConnectToSessionRequest, ConnectToSessionResponse>,
) : ConnectToSessionRepository {

    override fun connectToSession(sessionId: String): Flow<Result<Session, ErrorType>> = flow {
        val userId = userDataRepository.getUserId()
        val response = httpNetworkClient.getResponse(
            ConnectToSessionRequest.ConnectToSession(
                sessionId = sessionId,
                userId = userId
            )
        )
        when (response.body) {
            is ConnectToSessionResponse.ConnectToSession -> {
                emitAll(getSession(sessionId, userId))
            }

            else -> {
                emit(Result.Error(response.resultCode.mapToErrorType()))
            }
        }
    }

    private fun getSession(sessionId: String, userId: String): Flow<Result<Session, ErrorType>> =
        flow {
            val response = httpNetworkClient.getResponse(
                ConnectToSessionRequest.Session(
                    sessionId = sessionId,
                    userId = userId
                )
            )
            when (val body = response.body) {
                is ConnectToSessionResponse.Session -> {
                    emit(Result.Success(body.value.toDomain()))
                }

                else -> {
                    emit(Result.Error(response.resultCode.mapToErrorType()))
                }
            }
        }

    // из CreateSessionRepositoryImpl
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
        val TAG = ConnectToSessionRepositoryImpl::class.simpleName
    }
}
