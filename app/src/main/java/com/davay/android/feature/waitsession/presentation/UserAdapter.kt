package com.davay.android.feature.waitsession.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davai.uikit.TagView
import com.davay.android.feature.waitsession.domain.User

class UserAdapter :
    RecyclerView.Adapter<UserViewHolder>() {

    val itemList: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val tagView = TagView(parent.context)
        return UserViewHolder(tagView)
    }

    override fun getItemCount(): Int = itemList.count()

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        if (position == 0) {
            holder.bind(itemList[position], true)
        } else {
            holder.bind(itemList[position], false)
        }
    }
}