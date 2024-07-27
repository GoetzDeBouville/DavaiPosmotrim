package com.davay.android.feature.createsession.domain.usecase

import com.davay.android.core.domain.models.CompilationFilms
import com.davay.android.core.domain.models.ErrorType
import com.davay.android.core.domain.models.Result
import com.davay.android.feature.createsession.domain.api.CreateSessionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionsUseCase @Inject constructor(
    private val createSessionRepository: CreateSessionRepository
) {
    fun execute(): Flow<Result<List<CompilationFilms>, ErrorType>> {
        return createSessionRepository.getCollections()
    }
}