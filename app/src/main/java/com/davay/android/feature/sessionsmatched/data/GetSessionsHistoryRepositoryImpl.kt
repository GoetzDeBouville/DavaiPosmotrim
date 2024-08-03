package com.davay.android.feature.sessionsmatched.data

import com.davay.android.core.data.converters.toDomain
import com.davay.android.core.data.database.HistoryDao
import com.davay.android.core.domain.models.Session
import com.davay.android.feature.sessionsmatched.domain.GetSessionsHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSessionsHistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
) : GetSessionsHistoryRepository {

    override suspend fun getSessionsHistory(): List<Session>? =
        withContext(Dispatchers.IO) {
            @Suppress("TooGenericExceptionCaught")
            try {
                historyDao.getSessions().map { it.toDomain() }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
}
