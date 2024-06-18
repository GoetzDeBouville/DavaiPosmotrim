package com.davay.android.base

import android.os.Bundle
import androidx.annotation.IdRes

sealed class NavigationCommand {
    class ToDirection(@IdRes val directions: Int, val bundle: Bundle? = null) : NavigationCommand()
    object Back : NavigationCommand()
}