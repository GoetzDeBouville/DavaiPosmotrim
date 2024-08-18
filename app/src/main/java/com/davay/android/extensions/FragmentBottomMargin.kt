package com.davay.android.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment

const val DENOMINATOR_FOUR = 4

fun Fragment.setBottomMargin(view: View) {
    ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
        val insetNavigationBar =
            windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
        // Если навигация перекрывает вью больше, чем на четверть, то делаем отступ
        if (v.height / DENOMINATOR_FOUR <= insetNavigationBar) {
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = insetNavigationBar
            }
        }
        windowInsets
    }
}