package com.davay.android.feature.coincidences.presentation.adapter

import androidx.recyclerview.widget.DiffUtil

class MoviesGridDiffCallback(
    private val oldItems: List<Any>,
    private val newItems: List<Any>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newItems[newItemPosition] === oldItems[oldItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        newItems[newItemPosition] == oldItems[oldItemPosition]
}