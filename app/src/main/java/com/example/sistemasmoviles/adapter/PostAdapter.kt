package com.example.sistemasmoviles.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Model.Respuesta

import com.example.sistemasmoviles.R

class PostAdapter(private val postsList: List<Respuesta>) : RecyclerView.Adapter<PostViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        return PostViewHolder(layoutInflater.inflate(R.layout.item_publicaciones,parent,false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = postsList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

}