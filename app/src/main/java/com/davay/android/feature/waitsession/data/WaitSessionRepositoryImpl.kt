package com.davay.android.feature.waitsession.data

import android.util.Log
import com.davay.android.BuildConfig
import com.davay.android.core.data.database.MovieIdDao
import com.davay.android.core.data.database.entity.MovieIdEntity
import com.davay.android.feature.waitsession.domain.api.WaitSessionRepository
import javax.inject.Inject

class WaitSessionRepositoryImpl @Inject constructor(private val movieIdDao: MovieIdDao) :
    WaitSessionRepository {
    override suspend fun saveMovieIdListToDb(idList: List<Int>) {
        @Suppress("TooGenericExceptionCaught")
        try {
            idList.forEach { id ->
                movieIdDao.insertMovieId(MovieIdEntity(movieId = id))
            }
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                Log.v(TAG, "exception -> $e")
            }
        }
    }

    private companion object {
        val TAG = WaitSessionRepositoryImpl::class.simpleName
    }
}