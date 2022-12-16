package com.example.sistemasmoviles.Controller

import android.content.Context
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
        val result: Call<Int> = service.setImage(imagen)

        result.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(context,"Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                Toast.makeText(context,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

}