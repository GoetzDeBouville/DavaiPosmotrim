package com.davay.android.feature.registration.presentation

import android.text.Editable
import android.util.Log
import com.davay.android.base.BaseViewModel
import com.davay.android.domain.usecases.SetSingleDataUseCase
import com.davay.android.feature.registration.di.DataType
import com.davay.android.feature.registration.di.Type
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID.randomUUID
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    @Type(DataType.USER_ID)
    private val setUserId: SetSingleDataUseCase<String>,
    @Type(DataType.USER_NAME)
    private val setUserName: SetSingleDataUseCase<String>
) : BaseViewModel() {

    private val _state = MutableStateFlow(RegistrationState.DEFAULT)
    val state: StateFlow<RegistrationState>
        get() = _state

    fun buttonClicked(text: Editable?) {
        textCheck(text)
        if (state.value == RegistrationState.SUCCESS) {
            setUserName.setSingleData(text.toString())
            val userId = randomUUID()
                .toString()
                .plus(USER_ID_POSTFIX)
                .plus(System.currentTimeMillis())
            Log.d("TAG", userId)
            setUserId.setSingleData(userId)
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
        private const val USER_ID_POSTFIX = "_android_"
    }
}
