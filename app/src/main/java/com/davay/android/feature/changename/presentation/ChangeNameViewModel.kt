package com.davay.android.feature.changename.presentation

import android.text.Editable
import com.davay.android.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ChangeNameViewModel @Inject constructor() : BaseViewModel() {

    private val _state = MutableStateFlow(ChangeNameState.DEFAULT)
    val state: StateFlow<ChangeNameState>
        get() = _state

    fun buttonClicked(text: Editable?) {
        textCheck(text)
//        if (state.value == ChangeNameState.SUCCESS){
//            что-то делаем
//        }
    }

    fun textCheck(text: Editable?) {
        when {
            text.isNullOrBlank() -> _state.value = ChangeNameState.FIELD_EMPTY
            text.length == TEXT_LENGTH_MIN -> _state.value = ChangeNameState.MINIMUM_LETTERS
            text.length > TEXT_LENGTH_MAX -> _state.value = ChangeNameState.MAXIMUM_LETTERS
            else -> {
                for (i in text.indices) {
                    if (!text[i].isLetter()) {
                        _state.value = ChangeNameState.NUMBERS
                        return
                    }
                }
                _state.value = ChangeNameState.SUCCESS
            }
        }
    }

    companion object {
        private const val TEXT_LENGTH_MIN = 1
        private const val TEXT_LENGTH_MAX = 16
    }
}