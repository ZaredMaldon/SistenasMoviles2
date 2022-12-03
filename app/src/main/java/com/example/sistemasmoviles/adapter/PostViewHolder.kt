package com.example.sistemasmoviles.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Model.Publicacion
import com.example.sistemasmoviles.Model.Respuesta
import com.example.sistemasmoviles.R

class PostViewHolder(view:View) :RecyclerView.ViewHolder(view){
    val Nombre=view.findViewById<TextView>(R.id.tv_PublicacionName)
    val Edad=view.findViewById<TextView>(R.id.tv_PublicacionEdad)
    val Tipo=view.findViewById<TextView>(R.id.tv_PublicacionTipo)
    val likes=view.findViewById<TextView>(R.id.tv_PublicacionLikes)
    val photo=view.findViewById<ImageView>(R.id.iv_Publiaciones)

    fun render(publicacion: Respuesta){
        Nombre.text = publicacion.Nombre
        Edad.text=publicacion.Edad
        Tipo.text=publicacion.Tipo
        likes.text= publicacion.MeGusta.toString()
    }
}