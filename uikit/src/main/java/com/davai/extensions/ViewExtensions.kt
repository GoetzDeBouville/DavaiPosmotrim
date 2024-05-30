package com.davai.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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

fun View.animateRevert(durationMs: Long = 300L) {
    val scaleX = ObjectAnimator.ofFloat(this, "scaleX", 2f, 1f)
    val scaleY = ObjectAnimator.ofFloat(this, "scaleY", 2f, 1f)
    val translationY = ObjectAnimator.ofFloat(this, "translationY", -5000f, 0f)
    val shackingY = ObjectAnimator.ofFloat(this, "translationY", 200f, -200f, 0f)

    val animatorSet1 = AnimatorSet().apply {
        playTogether(translationY, scaleX, scaleY)
        duration = durationMs
    }

    val animatorSet2 = AnimatorSet().apply {
        play(shackingY)
        duration = 100L
    }

    animatorSet1.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            animatorSet2.start()
        }
    })

    animatorSet1.start()
}

fun View.animateFadingOut(durationMs: Long = 1000L) {
    ObjectAnimator.ofFloat(this, "alpha", 1f, 0f).apply {
        duration = durationMs
        start()
    }
}