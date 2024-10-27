package com.davay.android.feature.match.presentation.animation

import android.view.View

interface AnimationMatchDialog {
    fun animateDialogDismiss(
        bottomSheet: View,
        onAnimationEnd: () -> Unit
    )

    fun animateBannerDrop(banner: View)
}