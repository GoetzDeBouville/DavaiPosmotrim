package com.davay.android.feature.sessionlist.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.R

class UserAdapter :
    RecyclerView.Adapter<UserViewHolder>() {
    val itemList: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun getItemCount(): Int = itemList.count()

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val context = holder.itemView.context
        val youSuffix = context.getString(R.string.session_list_you)
        if (position == 0) {
            holder.bind("${itemList[position]} $youSuffix")
        } else {
            holder.bind(itemList[position])
        }
    }
}