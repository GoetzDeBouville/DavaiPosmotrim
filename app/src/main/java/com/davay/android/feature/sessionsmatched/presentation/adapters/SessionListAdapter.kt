package com.davay.android.feature.sessionsmatched.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davay.android.databinding.ItemSessionBinding
import com.davay.android.domain.models.Session

class SessionListAdapter(
    private val onSessionClickListener: (id: String) -> Unit?
) : RecyclerView.Adapter<SessionListAdapter.SessionListViewHolder>() {

    private val sessionList = mutableListOf<Session>()

    class SessionListViewHolder(private val binding: ItemSessionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(session: Session) {
            val userList = session.users.toMutableList()
            userList[0] = userList[0].copy(name = userList[0].name + " (Вы)")
            binding.root.apply {
                setDate(session.date)
                setNamesList(
                    userList.joinToString(" ,")
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
        return SessionListViewHolder(binding)
    }

    override fun getItemCount() = sessionList.size

    override fun onBindViewHolder(holder: SessionListViewHolder, position: Int) {
        val session = sessionList[position]
        holder.bind(session)
        holder.itemView.setOnClickListener {
            onSessionClickListener.invoke(session.id)
        }
    }

    fun setData(sessions: List<Session>) {
        sessionList.clear()
        sessionList.addAll(sessions)
        notifyDataSetChanged()
    }
}