package com.example.sistemasmoviles.adapter

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import android.util.LogPrinter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.HTTP.RestEngine
import com.example.sistemasmoviles.HTTP.Service
import com.example.sistemasmoviles.Model.Respuesta
import com.example.sistemasmoviles.Model.RespuestaImagen
import com.example.sistemasmoviles.R
import com.example.sistemasmoviles.databinding.ItemPublicacionesusuarioBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback

@Suppress("DEPRECATION")
class PostViewHolderUsuario(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemPublicacionesusuarioBinding.bind(view)
    //val prefs = PreferenceManager.getDefaultSharedPreferences(binding.ivPubliaciones.context)


    fun render(
        publicacion: Respuesta,
        onClickListener: (Respuesta) -> Unit,
        onClickDelete: (Int) -> Unit,
        onClickUpdate:(Int) -> Unit,
        getId: (Int) -> Unit
    ){
        //prefs.getInt("idPublicacionEdit",0)
        getimage(publicacion.id)
        binding.tvPublicacionName.text = publicacion.Nombre
        binding.tvPublicacionEdad.text=publicacion.Edad
        binding.tvPublicacionTipo.text=publicacion.Tipo
        //Acciones
        binding.btnEliminar.setOnClickListener {
            onClickDelete(adapterPosition)
            getId(publicacion.id)
        }
        binding.btnEditar.setOnClickListener{
            onClickUpdate(adapterPosition)

        }
        itemView.setOnClickListener{
            onClickListener(publicacion)
        }
    }

    fun getimage(id: Int){
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<RespuestaImagen> = service.getPublicacionImage(id)

        result.enqueue(object: Callback<RespuestaImagen> {

            override fun onFailure(call: Call<RespuestaImagen>, t: Throwable){
                Log.e("Error en GetImage",t.toString())
            }

            override fun onResponse(call: Call<RespuestaImagen>, response: retrofit2.Response<RespuestaImagen>){
                //Toast.makeText(context, "Entre a respuesta image", Toast.LENGTH_SHORT).show()
                Picasso.with(binding.ivPubliaciones.context)
                    .load(response.body()!!.Url)//url
                    .error(R.mipmap.ic_launcher)
                    .fit()
                    .centerInside()
                    .into(binding.ivPubliaciones)
            }
        })
    }
}