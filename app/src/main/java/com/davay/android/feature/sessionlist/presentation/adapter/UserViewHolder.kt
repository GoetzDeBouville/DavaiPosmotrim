package com.davay.android.feature.sessionlist.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.davai.uikit.TagView

class UserViewHolder(
    private val tag: TagView
) : RecyclerView.ViewHolder(tag) {
    fun bind(user: User, isFirst: Boolean) {
        tag.changeStyle(TagView.Companion.Style.SECONDARY_GREEN)

        if (isFirst) {
            tag.setText("${user.name} (вы)")
        } else {
            tag.setText(user.name)
        }
    }
}