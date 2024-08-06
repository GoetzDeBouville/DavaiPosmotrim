package com.davay.android.feature.registration.presentation

enum class RegistrationState(val message: String) {
    LOADING(""),
    FIELD_EMPTY("Введите имя"),
    MINIMUM_LETTERS("Минимум две буквы"),
    NUMBERS("Только буквы"),
    DEFAULT(""),
    SUCCESS(""),
    MAXIMUM_LETTERS("Не более 16 символов"),
    CORRECT(""),
    NETWORK_ERROR("Проблемы с сетью, попробуйте позже")
}
