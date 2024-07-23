package com.davay.android.feature.createsession.domain.api

import com.davay.android.domain.models.CompilationFilms
import com.davay.android.utils.network.Resource

interface GetCollectionsUseCase {
    suspend fun execute(): Resource<List<CompilationFilms>>
}