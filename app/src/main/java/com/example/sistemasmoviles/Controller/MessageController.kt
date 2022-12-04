package com.example.sistemasmoviles.Controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Model.Message
import com.example.sistemasmoviles.R

class MessageController(private val user: String): RecyclerView.Adapter<MessageController.MessageViewHolder>() {
    private var messages: List<Message> = emptyList()

    fun setData(list: List<Message>){
        messages = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.message_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]

    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}