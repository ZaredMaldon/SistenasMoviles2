package com.example.sistemasmoviles.HTTP

import com.example.sistemasmoviles.Model.ImagenPubli
import com.example.sistemasmoviles.Model.Publicacion
import com.example.sistemasmoviles.Model.Respuesta
import com.example.sistemasmoviles.Model.RespuestaImagen
import retrofit2.Call
import retrofit2.http.*

interface Service {
    //Servicios para consumir Publicacion
    @GET("publicaciones")
    fun getPublicaciones(): Call<List<Respuesta>>

    @GET("publicaciones/{id}")
    fun getPublicacion(@Path("id") id: Int?): Call<List<Respuesta>>

    @GET("publicaciones/image/{id}")
    fun getPublicacionImage(@Path("id") id: Int): Call<RespuestaImagen>

    @Headers("Content-Type: application/json")
    @POST("publicaciones")
    fun setPubli(@Body publiData: Publicacion):Call<Int>


    /*@Headers("Content-Type: application/json")
    @POST("Album/Save")
    fun saveAlbum(@Body albumData: Publicacion): Call<Int>*/

    //Servicios para imagenes de Publicaciones
    @Headers("Content-Type: application/json")
    @POST("imagenes")
    fun setImage(@Body imageData:ImagenPubli):Call<Int>
}