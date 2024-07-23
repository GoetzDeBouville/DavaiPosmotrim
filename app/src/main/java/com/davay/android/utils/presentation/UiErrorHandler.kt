package com.davay.android.utils.presentation

import android.view.View
import com.davai.uikit.ErrorScreenView
import com.davay.android.domain.models.ErrorScreenState

interface UiErrorHandler {
    fun handleError(errorScreenState: ErrorScreenState, errorLayout: ErrorScreenView)
}