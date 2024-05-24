package com.davay.android.feature.selectmovie.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SwipeableLayoutManager(context: Context) : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams =
        RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (itemCount == 0 || state?.isPreLayout == true) {
            return
        }

        detachAndScrapAttachedViews(recycler!!)
        for (i in 0 until itemCount) {
            val view = recycler.getViewForPosition(i)
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
            break
        }
    }

    fun swipeLeft() {
        // Implement auto swipe left logic
    }

    fun swipeRight() {
        // Implement auto swipe right logic
    }

    fun revertSwipe() {
        // Implement revert swipe logic
    }

}
