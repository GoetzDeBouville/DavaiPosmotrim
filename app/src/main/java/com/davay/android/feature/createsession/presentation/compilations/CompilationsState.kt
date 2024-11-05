package com.davay.android.feature.createsession.presentation.compilations

import com.davay.android.core.domain.models.ErrorScreenState
import com.davay.android.feature.createsession.domain.model.CompilationSelect

sealed interface CompilationsState {
    data object Loading : CompilationsState
    class Error(val errorType: ErrorScreenState) : CompilationsState
    class Content(val compilationList: List<CompilationSelect>) : CompilationsState
    data object CreateSessionLoading : CompilationsState
}