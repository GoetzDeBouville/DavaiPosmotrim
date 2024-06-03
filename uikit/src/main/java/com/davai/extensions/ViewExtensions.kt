package com.davai.extensions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

fun View.animateUpscale(durationMs: Long = 1000L) {
    val scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1f, 2f)
    val scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1f, 2f)
    AnimatorSet().apply {
        playTogether(scaleX, scaleY)
        duration = durationMs
        start()
    }
}

fun View.animateFadingOut(durationMs: Long = 1000L) {
    ObjectAnimator.ofFloat(this, "alpha", 1f, 0f).apply {
        duration = durationMs
        start()
    }
}