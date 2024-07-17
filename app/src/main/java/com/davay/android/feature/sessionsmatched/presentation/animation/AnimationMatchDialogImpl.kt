package com.davay.android.feature.sessionsmatched.presentation.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.res.Resources
import android.view.View
import com.davai.extensions.dpToPx

class AnimationMatchDialogImpl : AnimationMatchDialog {
    override fun animateDialogDismiss(
        bottomSheet: View,
        onAnimationEnd: () -> Unit
    ) {
        val width = bottomSheet.width
        val height = bottomSheet.height

        val screenWidth = Resources.getSystem().displayMetrics.widthPixels

        val translationX =
            screenWidth - width.toFloat() / 2 - bottomSheet.paddingEnd - EXTERNAL_SPACING_24_DP.dpToPx()
        val translationY = -height.toFloat() / 2 + EXTERNAL_SPACING_24_DP.dpToPx()

        val scaleX = ObjectAnimator.ofFloat(bottomSheet, View.SCALE_X, 0f)
        val scaleY = ObjectAnimator.ofFloat(bottomSheet, View.SCALE_Y, 0f)
        val translateX = ObjectAnimator.ofFloat(bottomSheet, View.TRANSLATION_X, translationX)
        val translateY = ObjectAnimator.ofFloat(bottomSheet, View.TRANSLATION_Y, translationY)
        val alpha = ObjectAnimator.ofFloat(bottomSheet, View.ALPHA, 0f)

        val animatorSet = AnimatorSet().apply {
            playTogether(scaleX, scaleY, translateX, translateY, alpha)
            duration = MORPHING_ANIMATION_DURATION_500_MS
        }

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                onAnimationEnd.invoke()
            }
        })

        animatorSet.start()
    }

    override fun animateBannerDrop(banner: View) {
        val scaleX = ObjectAnimator.ofFloat(banner, View.SCALE_X, START_SCALE_X5, 1f)
        val scaleY = ObjectAnimator.ofFloat(banner, View.SCALE_Y, START_SCALE_X5, 1f)
        val alpha = ObjectAnimator.ofFloat(banner, View.ALPHA, 0f, 1f)

        scaleX.duration = BANNER_DROP_DURATION_500_MS
        scaleY.duration = BANNER_DROP_DURATION_500_MS
        alpha.duration = BANNER_DROP_DURATION_500_MS

        val animatorSet = AnimatorSet().apply {
            playTogether(scaleX, scaleY, alpha)
        }

        animatorSet.start()
    }

    private companion object {
        const val EXTERNAL_SPACING_24_DP = 24
        const val MORPHING_ANIMATION_DURATION_500_MS = 500L
        const val BANNER_DROP_DURATION_500_MS = 500L
        const val START_SCALE_X5 = 5f
    }
}