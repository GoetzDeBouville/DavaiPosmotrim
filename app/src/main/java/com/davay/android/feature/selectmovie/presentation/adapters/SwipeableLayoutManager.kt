package com.davay.android.feature.selectmovie.presentation.adapters

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.extensions.SwipeDirection
import com.davay.android.extensions.toggleSign

class SwipeableLayoutManager : RecyclerView.LayoutManager() {
    private var currentPosition = 0
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (itemCount == 0 || state?.isPreLayout == true) {
            return
        }

        recycler?.let {
            detachAndScrapAttachedViews(recycler)
            layoutCurrentView(recycler)
        }
    }

    private fun layoutCurrentView(recycler: RecyclerView.Recycler) {
        if (currentPosition < itemCount) {
            val view = recycler.getViewForPosition(currentPosition)
            addView(view)
            measureChildWithMargins(
                view,
                0,
                0
            )
            layoutDecoratedWithMargins(
                view,
                0,
                0,
                getDecoratedMeasuredWidth(view),
                getDecoratedMeasuredHeight(view)
            )
        }
    }

    fun moveNextAndLayout() {
        if (currentPosition < itemCount) {
            currentPosition++
            requestLayout()
        }
    }

    fun moveNextWithSwipeAndLayout(swipeDirection: SwipeDirection) {
        val currentView = getCurrentView()
        if (currentPosition < itemCount) {
            currentView?.let {
                animateSwipe(currentView, swipeDirection) {
                    currentPosition++
                    requestLayout()
                }
            }
        }
    }

    fun shiftLeftWithRevertAndLayout() {
        val previousView = getCurrentView()
        if (currentPosition > 0) {
            currentPosition--
            requestLayout()
            previousView?.let { animateRevert(previousView) }
        }
    }

    private fun getCurrentView(): View? {
        return if (currentPosition in 0 until itemCount) {
            findViewByPosition(currentPosition)
        } else {
            null
        }
    }

    private fun animateRevert(view: View, durationMs: Long = ANIMATION_DURATION_500_MS) {
        val scaleX = scaleX(view)
        val scaleY = scaleY(view)
        val translationY = translationY(view)

        AnimatorSet().apply {
            playTogether(translationY, scaleX, scaleY)
            duration = durationMs
            start()
        }
    }

    private fun scaleX(view: View): ObjectAnimator = ObjectAnimator.ofFloat(
        view,
        SCALE_X,
        MAX_SCALE_2X,
        1f
    )

    private fun scaleY(view: View): ObjectAnimator = ObjectAnimator.ofFloat(
        view,
        SCALE_Y,
        MAX_SCALE_2X,
        1f
    )

    private fun translationY(view: View): ObjectAnimator = ObjectAnimator.ofFloat(
        view,
        TRANSLATION_Y,
        TRANSLATION_Y_4000.toggleSign(),
        0f
    )

    private fun animateSwipe(
        view: View,
        swipeDirection: SwipeDirection,
        durationMs: Long = ANIMATION_DURATION_800_MS,
        onEnd: () -> Unit
    ) {
        var rotationAngle = ROTATION_ANGLE_15_DEG
        var translationDistance = TRANSLATION_X_2000

        if (swipeDirection == SwipeDirection.LEFT) {
            rotationAngle = ROTATION_ANGLE_15_DEG.toggleSign()
            translationDistance = TRANSLATION_X_2000.toggleSign()
        }

        val rotation = ObjectAnimator.ofFloat(
            view,
            ROTATION,
            0f,
            rotationAngle
        )
        val translationX = ObjectAnimator.ofFloat(
            view,
            TRANSLATION_X,
            0f,
            translationDistance
        )

        AnimatorSet().apply {
            playTogether(rotation, translationX)
            duration = durationMs
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.translationX = 0f
                    view.rotation = 0f
                    onEnd()
                }
            })
            start()
        }
    }

    companion object {
        const val ANIMATION_DURATION_800_MS = 800L
        const val ANIMATION_DURATION_500_MS = 500L
        const val ROTATION_ANGLE_15_DEG = 15f
        const val TRANSLATION_X_2000 = 3000f
        const val TRANSLATION_Y_4000 = 4000f
        const val MAX_SCALE_2X = 2f
        const val SCALE_X = "scaleX"
        const val SCALE_Y = "scaleY"
        const val TRANSLATION_X = "translationX"
        const val TRANSLATION_Y = "translationY"
        const val ROTATION = "rotation"
    }
}
