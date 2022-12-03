package com.example.sistemasmoviles.Controller

import com.example.sistemasmoviles.Model.Publicacion
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.sistemasmoviles.R
import java.util.ArrayList

class PublicacionController(
    private val publicacionesList: ArrayList<Publicacion>,
    private val resource: Int
) : RecyclerView.Adapter<PublicacionController.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            resource, viewGroup, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, index: Int) {
        val publicacion = publicacionesList[index]
        viewHolder.textViewMensaje.text = publicacion.nombre
    }

    override fun getItemCount(): Int {
        return publicacionesList.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

        val textViewMensaje: TextView

        init {
            textViewMensaje = view.findViewById<View>(R.id.textViewPublicacion) as TextView
        }
    }
}