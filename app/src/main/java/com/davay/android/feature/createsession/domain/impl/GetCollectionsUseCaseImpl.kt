package com.davay.android.feature.createsession.domain.impl

import com.davay.android.domain.models.CompilationFilms
import com.davay.android.feature.createsession.domain.api.CreateSessionRepository
import com.davay.android.feature.createsession.domain.api.GetCollectionsUseCase
import com.davay.android.utils.network.Resource
import javax.inject.Inject

class GetCollectionsUseCaseImpl @Inject constructor(
    private val repository: CreateSessionRepository
) : GetCollectionsUseCase {
    override suspend fun execute(): Resource<List<CompilationFilms>> {
        return when (val resource = repository.getCollections()) {
            is Resource.Success -> {
                Resource.Success(resource.data)
            }

            is Resource.Error -> resource
        }
    }
}