package com.davay.android.feature.registration.presentation

import android.text.Editable
import android.util.Log
import com.davay.android.base.BaseViewModel
import com.davay.android.core.domain.models.User
import com.davay.android.core.domain.models.UserDataFields
import com.davay.android.core.domain.usecases.SetUserDataUseCase
import com.davay.android.feature.registration.domain.usecase.SetToNetworkUserDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID.randomUUID
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val setUserData: SetUserDataUseCase,
    private val setToNetworkUserDataUseCase: SetToNetworkUserDataUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(RegistrationState.DEFAULT)
    val state: StateFlow<RegistrationState>
        get() = _state

    fun buttonClicked(text: Editable?) {
        textCheck(text)
        if (state.value == RegistrationState.SUCCESS) {
            val userId = randomUUID().toString()
            val userName = text.toString()
            setUserData.setUserData(UserDataFields.UserName(userName = userName))
            setUserData.setUserData(UserDataFields.UserId(userId = userId))
            runSafelyUseCase(
                useCaseFlow = setToNetworkUserDataUseCase.setUserData(
                    User(
                        userId = userId,
                        name = userName
                    )
                ),
                onSuccess = { result ->
                    Log.d("TAG_CHANGE_NAME", result.toString())
                }
            )
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
    }
}
