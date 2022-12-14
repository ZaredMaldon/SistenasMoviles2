package com.example.sistemasmoviles.Controller

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.util.LogPrinter
import androidx.core.content.PackageManagerCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.sistemasmoviles.HTTP.RestEngine
import com.example.sistemasmoviles.HTTP.Service
import com.example.sistemasmoviles.Model.ImagenPubli
import com.example.sistemasmoviles.Model.Publicacion
import com.example.sistemasmoviles.Model.Respuesta
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.io.IOException


class PublicacionController(var pub:Publicacion) {
    private var Fk_Publicacion=0
    private var intento = 0


    fun agregar(links:List<String>,context: Context){//POST

            val service: Service = RestEngine.getRestEngine().create(Service::class.java)
            val result: Call<Int> = service.setPubli(pub)

        Log.e("Entre",result.toString())

            result.enqueue(object : Callback<Int> {
                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Fk_Publicacion = 0
                }

                override fun onResponse(call: Call<Int>, response: retrofit2.Response<Int>) {
                    if (response.isSuccessful) {
                        Fk_Publicacion = response.body()!!

                        for(item in links){//Agregar imagenes de la publicacion
                            Log.e("Entre",item.toString())
                            var imagen= ImagenPubli(item,Fk_Publicacion)
                            ImagenController(imagen).agregarImagen(context)
                        }

                    }
                }
            })

    }

    fun editar(id:Int){
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<Int> = service.editPubli(id,pub)

        result.enqueue(object: Callback<Int> {
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("No Realizado",t.message.toString())
            }

            override fun onResponse(call: Call<Int>, response: retrofit2.Response<Int>) {
                if(response.isSuccessful){
                    Log.i("Realizado","Cambio Realizado con exito")
                }
            }
        })
    }

}
