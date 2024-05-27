package com.davay.android.feature.waitsession.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davai.uikit.TagView

class UserAdapter :
    RecyclerView.Adapter<UserViewHolder>() {

    val itemList: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val tagView = TagView(parent.context)
        return UserViewHolder(tagView)
    }

    override fun getItemCount(): Int = itemList.count()

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        if (position == 0) {
            holder.bind("${itemList[position]} (вы)")
        } else {
            holder.bind(itemList[position])
        }
    }
}