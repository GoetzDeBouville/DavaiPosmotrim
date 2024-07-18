package com.davay.android.feature.selectmovie.presentation.animation

import android.view.View

interface IncrementAnimation {
    fun animate(view: View, onAnimationEnd: () -> Unit)
}