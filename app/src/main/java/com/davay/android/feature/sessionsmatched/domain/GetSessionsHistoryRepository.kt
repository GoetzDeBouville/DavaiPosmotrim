package com.davay.android.feature.sessionsmatched.domain

import com.davay.android.domain.models.Session

interface GetSessionsHistoryRepository {
    suspend fun getSessionsHistory(): List<Session>
}
