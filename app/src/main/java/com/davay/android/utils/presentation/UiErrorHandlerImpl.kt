package com.davay.android.utils.presentation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.davai.uikit.ErrorScreenView
import com.davay.android.core.domain.models.ErrorScreenState

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

            if (errorScreenState == ErrorScreenState.APP_VERSION_ERROR) {
                setButtonClickListener {
                    action?.invoke()
                    openAppInPlayStore(errorLayout.context)
                }
            } else if (action != null) {
                setButtonClickListener { action.invoke() }
            }
        }
    }

    /**
     * Переход на страницу приложения в PlayMarket
     * !Уточнить App id
     */
    private fun openAppInPlayStore(context: Context) = try {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(WEB_ADDRESS_INTENT + APP_ID))
        )
    } catch (e: ActivityNotFoundException) {
        Log.v(TAG, "Play Store not found, opening web link. ${e.localizedMessage}")
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(WEB_ADDRESS + APP_ID)
            )
        )
    }

    private companion object {
        val TAG = UiErrorHandlerImpl::class.simpleName
        const val APP_ID = "com.davay.DavayPosmotrim"
        const val WEB_ADDRESS = "https://play.google.com/store/apps/details?id="
        const val WEB_ADDRESS_INTENT = "market://details?id="
    }
}