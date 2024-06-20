package com.davay.android.feature.roulette.presentation.useradapter

import androidx.recyclerview.widget.RecyclerView
import com.davai.uikit.TagView
import com.davay.android.databinding.ItemParticipantsBinding
import com.davay.android.feature.roulette.presentation.model.UserRouletteModel

class UserViewHolder(
    private val binding: ItemParticipantsBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: UserRouletteModel) {
        binding.root.setText(user.name)
        if (user.isConnected) {
            binding.root.changeStyle(TagView.Companion.Style.SECONDARY_GREEN)
        } else {
            binding.root.changeStyle(TagView.Companion.Style.SECONDARY_GRAY)
        }
    }

    fun bindStyle(user: UserRouletteModel) {
        if (user.isConnected) {
            binding.root.changeStyle(TagView.Companion.Style.SECONDARY_GREEN)
        } else {
            binding.root.changeStyle(TagView.Companion.Style.SECONDARY_GRAY)
        }
    }
}
