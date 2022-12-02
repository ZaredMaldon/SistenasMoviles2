package com.example.sistemasmoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ActivityChats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)
    }

    fun onClickHome(view: View) {
        val change = Intent(this,ActivityPublicaciones::class.java)
        startActivity(change)
    }

    fun onClickMensajes(view: View) {
        val change = Intent(this,ActivityChats::class.java)
        startActivity(change)
    }

    fun onClickBuscar(view: View) {
        val change = Intent(this,ActivityBuscar::class.java)
        startActivity(change)
    }
}