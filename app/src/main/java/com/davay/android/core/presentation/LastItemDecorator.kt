package com.davay.android.core.presentation

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LastItemDecorator(private val spacing: Int) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        rect: Rect,
        view: View,
        parent: RecyclerView,
        s: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == s.itemCount - 1) {
            rect.bottom = spacing
        }
    }
}