package com.example.sistemasmoviles.Controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Model.Chat
import com.example.sistemasmoviles.R

class ChatController(val chatClick: (Chat) -> Unit): RecyclerView.Adapter<ChatController.ChatViewHolder>() {
    var chats: List<Chat> = emptyList()

    fun setData(list: List<Chat>){
        chats = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.chat_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

        holder.chatNameText.text = chats[position].name
        holder.usersTextView.text = chats[position].users.toString()

        holder.itemView.setOnClickListener {
            chatClick(chats[position])
        }
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    inner class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val chatNameText: TextView
        val usersTextView: TextView

        init {
            chatNameText = itemView.findViewById<View>(R.id.chatNameText) as TextView
            usersTextView = itemView.findViewById<View>(R.id.usersTextView) as TextView
        }
    }


}