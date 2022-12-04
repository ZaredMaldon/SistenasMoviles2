package com.example.sistemasmoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Controller.ChatController
import com.example.sistemasmoviles.Controller.MessageController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityChat : AppCompatActivity() {
    private var chatId = ""
    private var user = ""

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        intent.getStringExtra("chatId")?.let { chatId = it }
        intent.getStringExtra("user")?.let { user = it }

        if (chatId.isNotEmpty() && user.isNotEmpty()) {
            initViews()
        }
    }

    private fun initViews(){
        val messagesRecylerView = findViewById<RecyclerView>(R.id.messagesRecylerView)
        messagesRecylerView.layoutManager = LinearLayoutManager(this)
        messagesRecylerView.adapter = MessageController(user)

        val chatRef = db.collection("chats").document(chatId)

    }



}