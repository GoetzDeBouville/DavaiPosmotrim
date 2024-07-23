package com.davay.android.feature.createsession.domain.api

import com.davay.android.domain.models.CompilationFilms
import com.davay.android.domain.models.Genre
import com.davay.android.utils.network.Resource

interface CreateSessionRepository {
    suspend fun getGenres(): Resource<List<Genre>>
    suspend fun getCollections(): Resource<List<CompilationFilms>>
}