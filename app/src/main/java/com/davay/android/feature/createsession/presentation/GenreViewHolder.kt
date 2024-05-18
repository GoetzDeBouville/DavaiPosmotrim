package com.davay.android.feature.createsession.presentation

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.feature.createsession.domain.model.Genre

class GenreViewHolder(
    private val textView: TextView,
    private val clickListener: GenreAdapter.ItemClickListener
) : RecyclerView.ViewHolder(textView) {
    fun bind(genre: Genre) {
        textView.text = genre.name
        textView.setOnClickListener {
            if (genre.isSelected) {
                textView.background = ResourcesCompat.getDrawable(
                    itemView.context.resources,
                    com.davai.uikit.R.drawable.item_background_unselected,
                    itemView.context.theme
                )
                textView.setTextColor(
                    itemView.resources.getColor(
                        com.davai.uikit.R.color.text_caption_dark,
                        itemView.context.theme
                    )
                )
                genre.isSelected = false
            } else {
                textView.background = ResourcesCompat.getDrawable(
                    itemView.context.resources,
                    com.davai.uikit.R.drawable.item_background_selected,
                    itemView.context.theme
                )
                textView.setTextColor(
                    itemView.resources.getColor(
                        com.davai.uikit.R.color.text_base,
                        itemView.context.theme
                    )
                )
                genre.isSelected = true
            }
            clickListener.onClick(genre)
        }
    }
}
