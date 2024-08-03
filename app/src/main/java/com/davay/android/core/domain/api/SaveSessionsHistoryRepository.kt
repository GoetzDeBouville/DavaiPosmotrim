package com.davay.android.core.domain.api

import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.core.domain.models.Session

interface SaveSessionsHistoryRepository {
    suspend fun saveSessionsHistory(session: Session, movies: List<MovieDetails>)
}