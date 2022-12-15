package com.example.sistemasmoviles.HTTP

import com.example.sistemasmoviles.Model.*
import retrofit2.Call
import retrofit2.http.*

interface Service {
    //Servicios para consumir Publicacion

    @GET("publicaciones")
    fun getPublicaciones(): Call<List<Respuesta>>

    @GET("publicaciones/{buscarpor}")
    fun getPublicaciones(@Path("buscarpor") buscarpor:String): Call<List<Respuesta>>

    @GET("publicaciones/user/{Usuario}")
    fun getPublicacionByUser(@Path("Usuario") Usuario: String): Call<List<Respuesta>>

    @GET("publicaciones/image/{id}")
    fun getPublicacionImage(@Path("id") id: Int): Call<RespuestaImagen>

    @Headers("Content-Type: application/json")
    @POST("publicaciones")
    fun setPubli(@Body publiData: Publicacion):Call<Int>

    @PUT("publicaciones/{id}")
    fun editPubli(@Path("id") id:Int):Call<List<Respuesta>>

    @DELETE("publicaciones/{id}")
    fun deletePublicacion(@Path("id") id: Int):Call<Void>


    /*@Headers("Content-Type: application/json")
    @POST("Album/Save")
    fun saveAlbum(@Body albumData: Publicacion): Call<Int>*/

    //Servicios para imagenes de Publicaciones
    @Headers("Content-Type: application/json")
    @POST("imagenes")
    fun setImage(@Body imageData:ImagenPubli):Call<Int>
}