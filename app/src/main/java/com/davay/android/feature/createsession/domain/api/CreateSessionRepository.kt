package com.davay.android.feature.createsession.domain.api

import com.davay.android.core.domain.models.CompilationFilms
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Genre
import com.davay.android.core.domain.models.Result
import com.davay.android.core.domain.models.Session
import kotlinx.coroutines.flow.Flow

interface CreateSessionRepository {
    fun getCollections(): Flow<Result<List<CompilationFilms>, ErrorType>>
    fun getGenres(): Flow<Result<List<Genre>, ErrorType>>
    fun createSession(parameter: String, requestBody: List<String>): Flow<Result<Session, ErrorType>>
}