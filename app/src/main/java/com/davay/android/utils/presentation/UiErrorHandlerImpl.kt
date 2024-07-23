package com.davay.android.utils.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.davai.uikit.ErrorScreenView
import com.davay.android.domain.models.ErrorScreenState

class UiErrorHandlerImpl : UiErrorHandler {
    override fun handleError(
        errorScreenState: ErrorScreenState,
        errorLayout: ErrorScreenView,
        action: (() -> Unit)?
    ) {
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

            if (action == null) {
                openAppInPlayStore(errorLayout.context)
            } else {
                action.invoke()
            }
        }
    }

    /**
     * Переход на страницу приложения в PlayMarket
     * !Уточнить App id
     */
    private fun openAppInPlayStore(context: Context) {
        try {
            context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(WEB_ADDRESS + APP_ID))
            )
        } catch (e: Exception) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(WEB_ADDRESS_INTENT + APP_ID)
                )
            )
        }
    }

    private companion object {
        const val APP_ID = "com.davay.DavayPosmotrim"
        const val WEB_ADDRESS = "https://play.google.com/store/apps/details?id="
        const val WEB_ADDRESS_INTENT = "market://details?id="
    }
}