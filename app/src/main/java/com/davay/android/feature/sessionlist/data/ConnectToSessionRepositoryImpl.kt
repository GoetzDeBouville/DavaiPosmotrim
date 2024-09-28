package com.davay.android.feature.sessionlist.data

import com.davay.android.core.data.database.MovieIdDao
import com.davay.android.core.data.network.HttpNetworkClient
import com.davay.android.core.domain.api.UserDataRepository
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.core.domain.models.Session
import com.davay.android.feature.sessionlist.data.network.ConnectToSessionRequest
import com.davay.android.feature.sessionlist.data.network.ConnectToSessionResponse
import com.davay.android.feature.sessionlist.domain.api.ConnectToSessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConnectToSessionRepositoryImpl @Inject constructor(
    private val movieIdDao: MovieIdDao,
    private val userDataRepository: UserDataRepository,
    private val httpNetworkClient: HttpNetworkClient<ConnectToSessionRequest, ConnectToSessionResponse>,
) : ConnectToSessionRepository {
    override fun connectToSession(sessionId: String): Flow<Result<Session, ErrorType>> {
        TODO("Not yet implemented")
    }
}