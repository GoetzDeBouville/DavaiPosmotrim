package com.davay.android.feature.createsession.domain.impl

import com.davay.android.domain.models.Genre
import com.davay.android.feature.createsession.domain.api.CreateSessionRepository
import com.davay.android.feature.createsession.domain.api.GetGenresUseCase
import com.davay.android.utils.network.Resource
import javax.inject.Inject

class GetGenresUseCaseImpl @Inject constructor(
    private val repository: CreateSessionRepository
) : GetGenresUseCase {
    override suspend fun execute(): Resource<List<Genre>> {
        return when (val resource = repository.getGenres()) {
            is Resource.Success -> {
                Resource.Success(resource.data)
            }

            is Resource.Error -> resource
        }
    }
}