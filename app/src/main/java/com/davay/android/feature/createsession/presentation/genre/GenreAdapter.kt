package com.davay.android.feature.createsession.presentation.genre

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.databinding.GenreItemBinding
import com.davay.android.feature.createsession.domain.model.Genre

class GenreAdapter(private val clickListener: ItemClickListener) :
    RecyclerView.Adapter<GenreViewHolder>() {

    private val itemList: MutableList<Genre> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GenreViewHolder(
            GenreItemBinding.inflate(inflater, parent, false),
            clickListener
        ).also { Log.d("MyTag", "onCreateViewHolder " + it.toString()) }
    }

    override fun getItemCount(): Int = itemList.count()

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        Log.d("MyTag", "bind " + itemList[position].toString())
        holder.bind(itemList[position])
    }

    fun addItemList(list: List<Genre>) {
        itemList.addAll(list).also { Log.d("MyTag", itemList.toString()) }
    }

    fun interface ItemClickListener {
        fun onClick(genre: Genre)
    }
}
