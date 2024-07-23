package com.davay.android.feature.createsession.domain.api

import com.davay.android.domain.models.Genre
import com.davay.android.utils.network.Resource

interface GetGenresUseCase {
    suspend fun execute(): Resource<List<Genre>>
}