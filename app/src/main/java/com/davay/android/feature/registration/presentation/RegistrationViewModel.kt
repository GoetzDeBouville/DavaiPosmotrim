package com.davay.android.feature.registration.presentation

import android.text.Editable
import com.davay.android.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegistrationViewModel @Inject constructor() : BaseViewModel() {

    private val _state = MutableStateFlow(RegistrationState.DEFAULT)
    val state: StateFlow<RegistrationState>
        get() = _state

    fun buttonClicked(text: Editable?) {
        textCheck(text)
//        if (state.value == RegistrationState.SUCCESS){
//            что-то делаем
//        }
    }

    fun textCheck(text: Editable?) {
        when {
            text.isNullOrBlank() -> _state.value = RegistrationState.FIELD_EMPTY
            text.length == TEXT_LENGTH_MIN -> _state.value = RegistrationState.MINIMUM_LETTERS
            text.length > TEXT_LENGTH_MAX -> _state.value = RegistrationState.MAXIMUM_LETTERS
            else -> {
                for (i in text.indices) {
                    if (!text[i].isLetter()) {
                        _state.value = RegistrationState.NUMBERS
                        return
                    }
                }
                _state.value = RegistrationState.SUCCESS
            }
        }
    }

    companion object {
        private const val TEXT_LENGTH_MIN = 1
        private const val TEXT_LENGTH_MAX = 16
    }
}
