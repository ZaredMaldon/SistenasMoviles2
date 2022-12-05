package com.example.sistemasmoviles.Controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Model.Message
import com.example.sistemasmoviles.R

class MessageController(private val user: String): RecyclerView.Adapter<MessageController.MessageViewHolder>() {

    private var messages: List<Message> = emptyList()

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

        if(user == message.from){
            holder.myMessageLayout.visibility = View.VISIBLE
            holder.otherMessageLayout.visibility = View.GONE

            holder.myMessageTextView.text = message.message
        } else {
            holder.myMessageLayout.visibility = View.GONE
            holder.otherMessageLayout.visibility = View.VISIBLE

            holder.othersMessageTextView.text = message.message
        }

    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun setData(list: List<Message>) {
        messages = list
        notifyDataSetChanged()
    }

    inner class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val myMessageLayout: ConstraintLayout
        val otherMessageLayout: ConstraintLayout

        val myMessageTextView: TextView
        val othersMessageTextView: TextView

        init{
            myMessageLayout = itemView.findViewById<View>(R.id.myMessageLayout) as ConstraintLayout
            otherMessageLayout = itemView.findViewById<View>(R.id.otherMessageLayout) as ConstraintLayout

            myMessageTextView = itemView.findViewById<View>(R.id.myMessageTextView) as TextView
            othersMessageTextView = itemView.findViewById<View>(R.id.othersMessageTextView) as TextView
        }
    }
}