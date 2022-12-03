package com.example.sistemasmoviles.HTTP

import com.example.sistemasmoviles.Model.Publicacion
import com.example.sistemasmoviles.Model.Respuesta
import retrofit2.Call
import retrofit2.http.*

interface Service {
    //Servicios para consumir el Album
    @GET("publicaciones")
    fun getPublicaciones(): Call<List<Respuesta>>

    @GET("publicaciones/{id}")
    fun getPublicacion(@Path("id") id: Int): Call<List<Respuesta>>

    /*@Headers("Content-Type: application/json")
    @POST("Album/Save")
    fun saveAlbum(@Body albumData: Publicacion): Call<Int>*/
}