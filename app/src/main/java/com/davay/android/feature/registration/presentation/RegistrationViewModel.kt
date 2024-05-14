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
    }

    fun textCheck(text: Editable?) {
        if (text.isNullOrBlank()) {
            _state.value = RegistrationState.FIELD_EMPTY
        } else if (text.length == 1) {
            _state.value = RegistrationState.MINIMUM_LETTERS
        } else if (text.length > 16) {
            _state.value = RegistrationState.MAXIMUM_LETTERS
        } else {
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
