package com.davay.android.utils

import android.content.Context
import com.davai.uikit.TagView

class GenreViewGenerator(private val context: Context) {
    fun createTagView(text: String): TagView {
        return TagView(context).apply {
            setText(text)
            changeStyle(TagView.Companion.Style.PRIMARY_VIOLET)
        }
    }
}