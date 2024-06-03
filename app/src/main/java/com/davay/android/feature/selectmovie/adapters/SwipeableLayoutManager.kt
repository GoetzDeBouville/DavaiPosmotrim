package com.davay.android.feature.selectmovie.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

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
            measureChildWithMargins(view, 0, 0)
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

    fun revertSwipe() {
        if (currentPosition > 0) {
            currentPosition--
            requestLayout()
        }
    }
}
