package com.davay.android.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment

const val DENOMINATOR_TWO = 2

fun Fragment.setBottomMargin(view: View) {
    ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
        val insetNavigationBar =
            windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
        if (v.height / DENOMINATOR_TWO <= insetNavigationBar && v.height > 0) {
            if (windowInsets.isVisible(WindowInsetsCompat.Type.ime())) {
                v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = 0
                }
            } else {
                v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    bottomMargin = insetNavigationBar
                }
            }

        }
        windowInsets
    }
}