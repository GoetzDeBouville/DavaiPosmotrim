package com.davay.android.feature.createsession.presentation.compilations.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.databinding.CompilationsItemBinding
import com.davay.android.feature.createsession.domain.model.CompilationSelect

class CompilationsAdapter(private val clickListener: ItemClickListener) :
    RecyclerView.Adapter<CompilationsViewHolder>() {

    private val itemList: MutableList<CompilationSelect> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompilationsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CompilationsViewHolder(
            CompilationsItemBinding.inflate(inflater, parent, false),
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

    override fun onBindViewHolder(holder: CompilationsViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun updateItemList(list: List<CompilationSelect>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearSelections() {
        itemList.forEachIndexed { index, compilationSelect ->
            if (compilationSelect.isSelected) {
                itemList[index] = compilationSelect.copy(isSelected = false)
            }
        }
        notifyDataSetChanged()
    }

    fun interface ItemClickListener {
        fun onClick(compilation: CompilationSelect)
    }
}