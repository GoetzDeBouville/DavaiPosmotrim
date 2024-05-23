package com.davay.android.feature.waitsession.presentation

import androidx.recyclerview.widget.RecyclerView
import com.davai.uikit.TagView
import com.davay.android.feature.waitsession.domain.User

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