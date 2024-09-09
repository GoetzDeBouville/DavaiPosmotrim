package com.davay.android.feature.createsession.presentation.genre

import com.davay.android.core.domain.models.ErrorScreenState
import com.davay.android.core.domain.models.Session
import com.davay.android.feature.createsession.domain.model.GenreSelect

sealed class GenreState {
    data object Loading : GenreState()
    class Error(val errorType: ErrorScreenState) : GenreState()
    class Content(val genreList: List<GenreSelect>) : GenreState()
    data object CreateSessionLoading : GenreState()
}
