package com.davay.android.feature.waitsession.presentation

import com.davay.android.R
import com.davay.android.base.BaseViewModel
import com.davay.android.feature.onboarding.presentation.OnboardingFragment
import com.davay.android.feature.waitsession.domain.SaveMovieIdListToDbUseCase
import com.davay.android.feature.waitsession.domain.api.WaitSessionOnBoardingInteractor
import kotlinx.coroutines.launch
import javax.inject.Inject

class WaitSessionViewModel @Inject constructor(
    private val waitSessionOnBoardingInteractor: WaitSessionOnBoardingInteractor,
    private val saveMovieIdListToDbUseCase : SaveMovieIdListToDbUseCase
) : BaseViewModel() {
    private var dbPrepared = false
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

    fun saveIdListToDb(idlist: List<Int>) {
        viewModelScope.launch {
            saveMovieIdListToDbUseCase.execute(idlist)
            dbPrepared = true
        }
    }

    fun navigateToNextScreen() {
        if (isFirstTimeLaunch()) {
            markFirstTimeLaunch()
            val bundle = Bundle().apply {
                putInt(
                    OnboardingFragment.ONBOARDING_KEY,
                    OnboardingFragment.ONBOARDING_INSTRUCTION_SET
                )
            }
            navigate(
                R.id.action_waitSessionFragment_to_onboardingFragment,
                bundle
            )
        } else {
            navigate(R.id.action_waitSessionFragment_to_selectMovieFragment)
        }
    }

    fun isDbPrepared() = dbPrepared
}