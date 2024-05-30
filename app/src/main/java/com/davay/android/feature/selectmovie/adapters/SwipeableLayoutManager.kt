package com.davay.android.feature.selectmovie.adapters

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.extensions.SwipeDirection
import com.davay.android.extensions.toggleSign
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager.Companion.ANIMATION_DURATION_100_MS
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager.Companion.ANIMATION_DURATION_300_MS
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager.Companion.ANIMATION_DURATION_500_MS
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager.Companion.MAX_SCALE_2X
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager.Companion.ROTATION
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager.Companion.ROTATION_ANGLE_15_DEG
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager.Companion.SCALE_X
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager.Companion.SCALE_Y
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager.Companion.TRANSLATION_X
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager.Companion.TRANSLATION_X_4000
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager.Companion.TRANSLATION_Y
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager.Companion.TRANSLATION_Y_5000
import com.davay.android.feature.selectmovie.adapters.SwipeableLayoutManager.Companion.TRANSLATION_Y_SHAKING_200

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

        detachAndScrapAttachedViews(recycler!!)
        layoutCurrentView(recycler)
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

    fun swipeLeft() {
        if (currentPosition < itemCount - 1) {
            currentPosition++
            requestLayout()
        }
    }

    fun swipeRight() {
        if (currentPosition < itemCount - 1) {
            currentPosition++
            requestLayout()
        }
    }

    fun swipeLeftOnClick() {
        val currentView = getCurrentView()
        if (currentPosition < itemCount - 1) {
            currentView?.animateSwipe(SwipeDirection.LEFT) {
                currentPosition++
                requestLayout()
            }
        }
    }

    fun swipeRightOnClick() {
        val currentView = getCurrentView()
        if (currentPosition < itemCount - 1) {
            currentView?.animateSwipe(SwipeDirection.RIGHT) {
                currentPosition++
                requestLayout()
            }
        }
    }

    fun revertSwipe() {
        val previousView = getCurrentView()
        if (currentPosition > 0) {
            currentPosition--
            requestLayout()
            previousView?.animateRevert()
        }
    }

    private fun getCurrentView(): View? {
        return if (currentPosition in 0 until itemCount) {
            findViewByPosition(currentPosition)
        } else {
            null
        }
    }

    companion object {
        const val ANIMATION_DURATION_100_MS = 100L
        const val ANIMATION_DURATION_300_MS = 300L
        const val ANIMATION_DURATION_500_MS = 500L
        const val ROTATION_ANGLE_15_DEG = 15f
        const val TRANSLATION_Y_5000 = 5000f
        const val TRANSLATION_Y_SHAKING_200 = 200f
        const val TRANSLATION_X_4000 = 4000f
        const val MAX_SCALE_2X = 2f
        const val SCALE_X = "scaleX"
        const val SCALE_Y = "scaleY"
        const val TRANSLATION_X = "translationX"
        const val TRANSLATION_Y = "translationY"
        const val ROTATION = "rotation"
    }
}

fun View.animateRevert(durationMs: Long = ANIMATION_DURATION_300_MS) {
    val scaleX = scaleX(this)
    val scaleY = scaleY(this)
    val translationY = translationY(this)
    val shackingY = shackingY(this)

    val animatorSet1 = AnimatorSet().apply {
        playTogether(translationY, scaleX, scaleY)
        duration = durationMs
    }

    val animatorSet2 = AnimatorSet().apply {
        play(shackingY)
        duration = ANIMATION_DURATION_100_MS
    }

    animatorSet1.addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            animatorSet2.start()
        }
    })

    animatorSet1.start()
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
    TRANSLATION_Y_5000.toggleSign(),
    0f
)

private fun shackingY(view: View): ObjectAnimator = ObjectAnimator.ofFloat(
    view,
    TRANSLATION_Y,
    TRANSLATION_Y_SHAKING_200,
    TRANSLATION_Y_SHAKING_200.toggleSign(),
    0f
)

fun View.animateSwipe(
    swipeDirection: SwipeDirection,
    durationMs: Long = ANIMATION_DURATION_500_MS,
    onEnd: () -> Unit
) {
    var rotationAngle = ROTATION_ANGLE_15_DEG
    var translationDistance = TRANSLATION_X_4000

    if (swipeDirection == SwipeDirection.LEFT) {
        rotationAngle = ROTATION_ANGLE_15_DEG.toggleSign()
        translationDistance = TRANSLATION_X_4000.toggleSign()
    }

    val rotation = ObjectAnimator.ofFloat(
        this,
        ROTATION,
        0f,
        rotationAngle
    )
    val translationX = ObjectAnimator.ofFloat(
        this,
        TRANSLATION_X,
        0f,
        translationDistance
    )

    AnimatorSet().apply {
        playTogether(rotation, translationX)
        duration = durationMs
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                this@animateSwipe.translationX = 0f
                this@animateSwipe.rotation = 0f
                onEnd()
            }
        })
        start()
    }
}
