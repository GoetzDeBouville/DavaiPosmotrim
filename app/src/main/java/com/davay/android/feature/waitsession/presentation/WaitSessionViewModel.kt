package com.davay.android.feature.waitsession.presentation

import com.davay.android.base.BaseViewModel
import com.davay.android.feature.waitsession.domain.WaitSessionOnBoardingInteractror
import javax.inject.Inject

class WaitSessionViewModel @Inject constructor(
    private val waitSessionOnBoardingInteractror: WaitSessionOnBoardingInteractror
) : BaseViewModel() {
    fun isFirstTimeLaunch(): Boolean {
        return waitSessionOnBoardingInteractror.isFirstTimeLaunch()
    }

    fun setFirstTimeLaunch() {
        waitSessionOnBoardingInteractror.setFirstTimeLaunch()
    }
}