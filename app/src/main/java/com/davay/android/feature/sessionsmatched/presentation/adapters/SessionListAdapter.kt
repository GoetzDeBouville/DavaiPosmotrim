package com.davay.android.feature.sessionsmatched.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.core.domain.models.Session
import com.davay.android.databinding.ItemSessionBinding
import com.davay.android.extensions.formatDate

class SessionListAdapter(
    private val onSessionClickListener: ((session: Session) -> Unit)?
) : RecyclerView.Adapter<SessionListAdapter.SessionListViewHolder>() {

    private val sessionList = mutableListOf<Session>()

    class SessionListViewHolder(private val binding: ItemSessionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(session: Session) {
            val userList = session.users.toMutableList()
            userList[0] = userList[0] + " (Вы)"
            val formatedDate = session.date.formatDate()
            binding.root.apply {
                setDate(formatedDate)
                setNamesList(
                    userList.joinToString(", ")
                )
                setCover(session.imgUrl)
                setCoincidences(session.numberOfMatchedMovies ?: 0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionListViewHolder {
        val binding = ItemSessionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val viewHolder = SessionListViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onSessionClickListener?.invoke(sessionList[position])
            }
        }
        return viewHolder
    }

    override fun getItemCount() = sessionList.size

    override fun onBindViewHolder(holder: SessionListViewHolder, position: Int) {
        val session = sessionList[position]
        holder.bind(session)
    }

    fun setData(sessions: List<Session>) {
        sessionList.clear()
        sessionList.addAll(sessions)
        notifyDataSetChanged()
    }
}