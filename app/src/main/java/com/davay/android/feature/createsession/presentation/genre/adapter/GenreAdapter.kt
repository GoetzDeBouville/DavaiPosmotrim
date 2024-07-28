package com.davay.android.feature.createsession.presentation.genre.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.databinding.GenreItemBinding
import com.davay.android.feature.createsession.domain.model.GenreSelect

class GenreAdapter(private val clickListener: ItemClickListener) :
    RecyclerView.Adapter<GenreViewHolder>() {

    private val itemList: MutableList<GenreSelect> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
//        return GenreViewHolder(
//            GenreItemBinding.inflate(inflater, parent, false),
//            clickListener
//        )
        return GenreViewHolder(
            GenreItemBinding.inflate(inflater, parent, false),
        ).apply {
            itemView.setOnClickListener {
                val item = itemList[adapterPosition]
                itemList[adapterPosition] = item.copy(isSelected = !item.isSelected)
                notifyItemChanged(adapterPosition)
                clickListener.onClick(itemList[adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = itemList.count()

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun addItemList(list: List<GenreSelect>) {
        itemList.addAll(list)
    }

    fun interface ItemClickListener {
        fun onClick(genre: GenreSelect)
    }
}
