package com.davay.android.feature.waitsession.presentation

import com.davay.android.R
import com.davay.android.base.BaseViewModel
import com.davay.android.feature.waitsession.domain.WaitSessionOnBoardingInteractor
import javax.inject.Inject

class WaitSessionViewModel @Inject constructor(
    private val waitSessionOnBoardingInteractor: WaitSessionOnBoardingInteractor
) : BaseViewModel() {
    fun isFirstTimeLaunch(): Boolean {
        return waitSessionOnBoardingInteractor.isFirstTimeLaunch()
    }

    fun markFirstTimeLaunch() {
        waitSessionOnBoardingInteractor.markFirstTimeLaunch()
    }

    /**
     * Метод необходим для обхода ошибки при возврате назад на экран создания сессии после
     * смены конфигурации устройства
     */
    fun navigateToCreateSession() {
        clearBackStackToMain()
        navigate(R.id.action_mainFragment_to_createSessionFragment)
    }
}