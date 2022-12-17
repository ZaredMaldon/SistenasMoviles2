package com.example.sistemasmoviles.Controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.sistemasmoviles.HTTP.RestEngine
import com.example.sistemasmoviles.HTTP.Service
import com.example.sistemasmoviles.Model.ImagenPubli
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImagenController (var imagen:ImagenPubli){

    fun agregarImagen(context: Context){//POST

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<Void> = service.setImage(imagen)

        result.enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context,t.message.toString(),Toast.LENGTH_LONG).show()
                Log.e("t.message",t.message.toString())
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(context,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

}