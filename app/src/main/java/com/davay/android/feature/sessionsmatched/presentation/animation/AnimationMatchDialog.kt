package com.davay.android.feature.sessionsmatched.presentation.animation

import android.view.View

interface AnimationMatchDialog {
    fun animateDialogDismiss(
        bottomSheet: View,
        onAnimationEnd: () -> Unit
    )

    fun animateBannerDrop(banner: View)
}