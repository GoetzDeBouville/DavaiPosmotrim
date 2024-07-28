package com.davay.android.feature.selectmovie.domain

import com.davay.android.core.domain.models.MovieDetails
import com.davay.android.core.domain.models.Session

interface SaveSessionsHistoryRepository {
    suspend fun saveSessionsHistory(session: Session, movies: List<MovieDetails>)
}