package com.davay.android.core.domain.models

import android.content.Context
import com.davay.android.R

enum class UserNameState(private val message: Int) {
    LOADING(R.string.empty_string),
    FIELD_EMPTY(R.string.registration_enter_name),
    MINIMUM_LETTERS(R.string.registration_two_letters_minimum),
    NUMBERS(R.string.registration_just_letters),
    DEFAULT(R.string.empty_string),
    SUCCESS(R.string.empty_string),
    MAXIMUM_LETTERS(R.string.registration_not_more_letters),
    CORRECT(R.string.empty_string),
    NETWORK_ERROR(R.string.registration_network_problem);

    fun getMessage(context: Context) =
        context.getString(message)
}
