package com.davay.android.feature.coincidences.presentation.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MoviesGridDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val marginInPx = view.resources.getDimensionPixelSize(com.davai.uikit.R.dimen.margin_16)
        outRect.apply {
            left = marginInPx
            right = marginInPx
            top = marginInPx
            bottom = marginInPx
        }

    }
}