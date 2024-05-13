package com.davay.android.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {

    private val _navigationEvents = MutableStateFlow<Int?>(null)
    val navigationEvents: StateFlow<Int?>
        get() = _navigationEvents

    fun navigate(actionId: Int) {
        _navigationEvents.value = actionId
    }
}