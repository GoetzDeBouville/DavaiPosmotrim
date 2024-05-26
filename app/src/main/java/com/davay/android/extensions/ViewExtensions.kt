package com.davay.android.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

private const val ROTATION_ANGLE_RANGE_45 = 45f
private const val ANIMATION_DURATION = 300L

fun View.animateSwipe(direction: SwipeDirection, onEnd: () -> Unit) {
    val translationX = if (direction == SwipeDirection.LEFT) -width.toFloat() else width.toFloat()
    val rotation = if (direction == SwipeDirection.LEFT) -ROTATION_ANGLE_RANGE_45 else ROTATION_ANGLE_RANGE_45

    val translationXAnimator = ObjectAnimator.ofFloat(this, "translationX", translationX)
    val translationYAnimator = ObjectAnimator.ofFloat(this, "translationY", 0f)
    val rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", rotation)

    val animatorSet = AnimatorSet().apply {
        playTogether(translationXAnimator, translationYAnimator, rotationAnimator)
        duration = ANIMATION_DURATION
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                onEnd.invoke()
            }
        })
    }

    animatorSet.start()
}