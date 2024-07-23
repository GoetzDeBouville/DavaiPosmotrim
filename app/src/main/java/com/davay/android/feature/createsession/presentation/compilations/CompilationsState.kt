package com.davay.android.feature.createsession.presentation.compilations

import com.davay.android.domain.models.ErrorScreenState
import com.davay.android.feature.createsession.domain.model.Compilation

sealed class CompilationsState {
    data object Loading : CompilationsState()
    class Error(val errorType: ErrorScreenState) : CompilationsState()
    class Content(val compilationList: List<Compilation>) : CompilationsState()
}