package com.davay.android.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> get() = _navigation

    fun navigate(navDirections: Int) {
        _navigation.value = Event(NavigationCommand.ToDirection(navDirections))
    }

    fun navigate(navDirections: Int, bundle: Bundle) {
        _navigation.value = Event(NavigationCommand.ToDirection(navDirections, bundle))
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }

}