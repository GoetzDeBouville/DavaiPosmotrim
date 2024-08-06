package com.davay.android.core.domain.models

enum class UserNameState(val message: String) {
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
