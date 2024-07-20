package com.davay.android.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavOptions
import com.davay.android.R

abstract class BaseViewModel : ViewModel() {

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> get() = _navigation

    fun navigate(@IdRes navDirections: Int) {
        _navigation.value = Event(NavigationCommand.ToDirection(navDirections))
    }

    fun navigate(@IdRes navDirections: Int, bundle: Bundle) {
        _navigation.value = Event(NavigationCommand.ToDirection(navDirections, bundle))
    }

    fun navigate(@IdRes navDirections: Int, navOptions: NavOptions) {
        _navigation.value = Event(NavigationCommand.ToDirection(navDirections, null, navOptions))
    }

    fun navigate(@IdRes navDirections: Int, bundle: Bundle, navOptions: NavOptions) {
        _navigation.value = Event(NavigationCommand.ToDirection(navDirections, bundle, navOptions))
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }

    /**
     * Метод чистит backstack до MainFragment и делает сооветствующий переход
     */
    fun clearBackStackToMain() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.main_navigation_graph, inclusive = true)
            .build()

        navigate(R.id.mainFragment, navOptions)
    }

    fun clearBackStackToMainAndNavigate(@IdRes navDirections: Int) {
        clearBackStackToMain()
        navigate(navDirections)
    }
}