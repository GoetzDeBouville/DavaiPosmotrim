package com.davay.android.base

import android.os.Bundle
import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class ToDirection(val directions: Int, val bundle: Bundle? = null) : NavigationCommand()
    object Back : NavigationCommand()
}