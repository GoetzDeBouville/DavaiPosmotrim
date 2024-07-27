package com.davay.android.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavOptions

sealed class NavigationCommand {
    class ToDirection(
        @IdRes val directions: Int,
        val bundle: Bundle? = null,
        val navOptions: NavOptions? = null
    ) : NavigationCommand()

    object Back : NavigationCommand()
}