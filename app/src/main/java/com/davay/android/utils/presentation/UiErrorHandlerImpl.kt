package com.davay.android.utils.presentation

import com.davai.uikit.ErrorScreenView
import com.davay.android.domain.models.ErrorScreenState

class UiErrorHandlerImpl : UiErrorHandler {
    override fun handleError(errorScreenState: ErrorScreenState, errorLayout: ErrorScreenView) {
        errorLayout.apply {
            val title = this.context.resources.getString(errorScreenState.errorTitleResource)
            val description =
                this.context.resources.getString(errorScreenState.errorDescriptionResource)
            val buttonText = if (errorScreenState.buttonTextResource != null) {
                this.context.resources.getString(errorScreenState.buttonTextResource)
            } else {
                ""
            }

            setErrorImage(errorScreenState.imageResource)
            setErrorTitle(title)
            setErrorDescription(description)
            setButtonText(buttonText)
        }
    }
}