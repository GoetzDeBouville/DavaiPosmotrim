package com.davay.android.feature.sessionsmatched.domain

import com.davay.android.core.domain.models.Session
import javax.inject.Inject

class GetSessionsHistoryUseCase @Inject constructor(
    private val repository: GetSessionsHistoryRepository
) {
    suspend fun execute(): List<Session>? {
        return repository.getSessionsHistory()
    }
}