package com.davay.android.feature.selectmovie.presentation.adapters

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeCallback(
    private val layoutManager: SwipeableLayoutManager,
    private val onSwipedLeft: () -> Unit,
    private val onSwipedRight: () -> Unit
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = 0
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when (direction) {
            ItemTouchHelper.LEFT -> {
                layoutManager.moveNextAndLayout()
                onSwipedLeft()
            }

            ItemTouchHelper.RIGHT -> {
                layoutManager.moveNextAndLayout()
                onSwipedRight()
            }
        }
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        viewHolder.itemView.apply {
            rotation = dX / recyclerView.width * ROTATION_ANGLE_RANGE_15
            translationX = dX
            translationY = dY
        }
        if (viewHolder is MovieCardAdapter.MovieCardVH) {
            viewHolder.updateSwipeTransition(dX)
        }
        super.onChildDraw(
            canvas,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.apply {
            rotation = 0f
            translationX = 0f
            translationY = 0f
        }
    }

    private companion object {
        const val ROTATION_ANGLE_RANGE_15 = 15
    }
}
