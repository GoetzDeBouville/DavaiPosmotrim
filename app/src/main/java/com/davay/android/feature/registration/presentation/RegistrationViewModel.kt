package com.davay.android.feature.registration.presentation

import android.text.Editable
import com.davay.android.base.BaseViewModel
import com.davay.android.domain.usecases.SetDataByKeyUseCase
import com.davay.android.utils.SharedKeys
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID.randomUUID
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val setUserData: SetDataByKeyUseCase<String>
) : BaseViewModel() {

    private val _state = MutableStateFlow(RegistrationState.DEFAULT)
    val state: StateFlow<RegistrationState>
        get() = _state

    fun buttonClicked(text: Editable?) {
        textCheck(text)
        if (state.value == RegistrationState.SUCCESS) {
            setUserData.setSharedPreferences(SharedKeys.USER_NAME, text.toString())
            val userId = randomUUID().toString().plus(USER_ID_POSTFIX)
            setUserData.setSharedPreferences(SharedKeys.USER_ID, userId)
        }
    }

    fun textCheck(text: Editable?) {
        val inputText = text?.toString().orEmpty()
        when {
            inputText.isBlank() -> _state.value = RegistrationState.FIELD_EMPTY
            inputText.length < TEXT_LENGTH_MIN -> _state.value = RegistrationState.MINIMUM_LETTERS
            inputText.length > TEXT_LENGTH_MAX -> _state.value = RegistrationState.MAXIMUM_LETTERS
            inputText.any { !it.isLetter() } -> _state.value = RegistrationState.NUMBERS
            else -> _state.value = RegistrationState.SUCCESS
        }
    }

    companion object {
        private const val TEXT_LENGTH_MIN = 2
        private const val TEXT_LENGTH_MAX = 16
        private const val USER_ID_POSTFIX = "_android"
    }
}
