package com.davay.android.feature.createsession.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.feature.createsession.domain.model.Genre

class GenreAdapter(private val clickListener: ItemClickListener) :
    RecyclerView.Adapter<GenreViewHolder>() {

    val itemList: MutableList<Genre> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val tv = LayoutInflater.from(parent.context)
            .inflate(com.davai.uikit.R.layout.item_text_view, parent, false)
        return GenreViewHolder(
            tv as TextView,
            clickListener
        )
    }

    override fun getItemCount(): Int = itemList.count()

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun interface ItemClickListener {
        fun onClick(genre: Genre)
    }
}
