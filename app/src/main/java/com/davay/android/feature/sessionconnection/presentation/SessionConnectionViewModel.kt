package com.davay.android.feature.sessionconnection.presentation

import android.text.Editable
import com.davay.android.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SessionConnectionViewModel @Inject constructor() : BaseViewModel() {

    private val _state = MutableStateFlow(SessionConnectionState.DEFAULT)
    val state: StateFlow<SessionConnectionState>
        get() = _state

    fun buttonClicked(text: Editable?) {
        textCheck(text)
//        if (state.value == RegistrationState.SUCCESS){
//            что-то делаем
//        }
    }

    fun textCheck(text: Editable?) {
        val inputText = text?.toString().orEmpty()
        when {
            inputText.isBlank() -> _state.value = SessionConnectionState.FIELD_EMPTY
            inputText.length != TEXT_LENGTH_7 -> _state.value = SessionConnectionState.INVALID_LENGTH
            else -> {
                if (inputText.count { it.isLetter() } != LETTER_COUNT_4 ||
                    inputText.count { it.isDigit() } != DIGIT_COUNT_3
                ) {
                    _state.value = SessionConnectionState.INVALID_FORMAT
                } else {
                    _state.value = SessionConnectionState.SUCCESS
                }
            }
        }
    }

    companion object {
        private const val TEXT_LENGTH_7 = 7
        private const val LETTER_COUNT_4 = 4
        private const val DIGIT_COUNT_3 = 3
    }
}