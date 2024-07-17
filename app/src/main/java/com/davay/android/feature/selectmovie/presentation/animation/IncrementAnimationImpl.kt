package com.davay.android.feature.selectmovie.presentation.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import com.davai.extensions.dpToPxFloat
import com.davay.android.extensions.toggleSign

class IncrementAnimationImpl : IncrementAnimation {
    override fun animate(view: View, onAnimationEnd: () -> Unit) {
        val translationY = ICNREMENT_Y_TRANSLATION_24_DP.toggleSign().dpToPxFloat()

        val translateY =
            ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, translationY)
        val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f)

        translateY.duration = INCREMENT_ANIMATION_1000_MS
        alpha.duration = INCREMENT_ANIMATION_1000_MS

        val animatorSet = AnimatorSet().apply {
            playTogether(translateY, alpha)
            addListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.apply {
                            this.translationY = 0f
                            this.alpha = 1f
                            visibility = View.INVISIBLE
                        }
                    }
                }
            )
        }

        view.visibility = View.VISIBLE
        animatorSet.apply {
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    onAnimationEnd.invoke()
                }
            })
            start()
        }
    }

    private companion object {
        const val INCREMENT_ANIMATION_1000_MS = 1000L
        const val ICNREMENT_Y_TRANSLATION_24_DP = 40
    }
}