package com.davay.android.utils.presentation

import com.davai.uikit.ErrorScreenView
import com.davay.android.domain.models.ErrorScreenState

/**
 * UiErrorHandler необходим для обработки отображения ошибок, чтобы не дублировать код в каждом фрагменте.
 * UiErrorHandler работает только с конкретным типом View - ErrorScreenView.
 */
interface UiErrorHandler {
    /**
     * Метод приминает унифицированный стэйт ошибки ErrorScreenState, кастомную View ErrorScreenView
     * и в action передается лямбда для слушателя кликов на кнопке в стэйте NO_INTERNET. В стэйте
     * APP_VERSION_ERROR будет отработан переход на страницу приложения в PlayMarket. В остальных
     * стэйтах кнопка не отображается.
     */
    fun handleError(
        errorScreenState: ErrorScreenState,
        errorLayout: ErrorScreenView,
        action: (() -> Unit)?
    )
}