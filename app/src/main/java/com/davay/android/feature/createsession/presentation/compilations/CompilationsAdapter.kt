package com.davay.android.feature.createsession.presentation.compilations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.databinding.CompilationsItemBinding
import com.davay.android.feature.createsession.domain.model.Compilation

class CompilationsAdapter(private val clickListener: ItemClickListener) :
    RecyclerView.Adapter<CompilationsViewHolder>() {

    private val itemList: MutableList<Compilation> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompilationsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CompilationsViewHolder(
            CompilationsItemBinding.inflate(inflater, parent, false),
            clickListener
        )
    }

    override fun getItemCount(): Int = itemList.count()

    override fun onBindViewHolder(holder: CompilationsViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun addItemList(list: List<Compilation>) {
        itemList.addAll(list)
    }

    fun interface ItemClickListener {
        fun onClick(compilation: Compilation)
    }
}