package com.davay.android.feature.sessionsmatched.data

import com.davay.android.data.converters.toDomain
import com.davay.android.data.database.HistoryDao
import com.davay.android.domain.models.Session
import com.davay.android.feature.sessionsmatched.domain.GetSessionsHistoryRepository
import javax.inject.Inject

class GetSessionsHistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
) : GetSessionsHistoryRepository {

    override suspend fun getSessionsHistory(): List<Session> =
        historyDao.getSessions().map { it.toDomain() }
}
