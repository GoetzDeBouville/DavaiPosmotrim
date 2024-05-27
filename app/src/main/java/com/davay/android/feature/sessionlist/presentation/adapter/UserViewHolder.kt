package com.davay.android.feature.sessionlist.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.davai.uikit.TagView

class UserViewHolder(
    private val tag: TagView
) : RecyclerView.ViewHolder(tag) {
    fun bind(user: String) {
        tag.changeStyle(TagView.Companion.Style.SECONDARY_GREEN)
        tag.setText(user)
    }
}
