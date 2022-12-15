package com.example.sistemasmoviles.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Model.Respuesta
import com.example.sistemasmoviles.R

class PostAdapterUsuario(
    private var postsList: List<Respuesta>,
    private val onClickListener:(Respuesta)->Unit,
    private val onClickDelete:(Int) -> Unit,
    private val getId:(Int)-> Unit
    ) : RecyclerView.Adapter<PostViewHolderUsuario>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolderUsuario {
        val layoutInflater= LayoutInflater.from(parent.context)
        return PostViewHolderUsuario(layoutInflater.inflate(R.layout.item_publicacionesusuario,parent,false))
    }

    override fun onBindViewHolder(holder: PostViewHolderUsuario, position: Int) {
        val item = postsList[position]
        holder.render(item,onClickListener,onClickDelete,getId)
    }

    override fun getItemCount(): Int = postsList.size
}