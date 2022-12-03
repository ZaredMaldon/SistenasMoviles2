package com.example.sistemasmoviles.Controller

import android.annotation.SuppressLint
import android.util.Log
import androidx.core.content.PackageManagerCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sistemasmoviles.Model.Publicacion
import org.json.JSONObject

class PublicacionController(var pub:Publicacion) {

    @SuppressLint("RestrictedApi")
     fun enviar(): JsonObjectRequest {
        // Inicializacion del Objeto JSON
        val jsonObject= JSONObject()
        jsonObject.put("Nombre",pub.Nombre)
        jsonObject.put("Edad",pub.Edad)
        jsonObject.put("Tipo",pub.Tipo)
        jsonObject.put("Descripcion",pub.Descripcion)
        jsonObject.put("imagen1",pub.imagen1)
        jsonObject.put("imagen2",pub.imagen2)
        jsonObject.put("imagen3",pub.imagen3)
        jsonObject.put("MeGusta",pub.MeGusta)
        jsonObject.put("Estatus",pub.Estatus)
        jsonObject.put("Usuario",pub.Usuario)

        //val queue = Volley.newRequestQueue(this)
        val url="https://apipublicaciones.zambiaa.com/api/publicaciones"
        val stringRequest= JsonObjectRequest(
            Request.Method.POST,url,jsonObject,
            Response.Listener { response ->
            Log.i(PackageManagerCompat.LOG_TAG,"Response is: $response")
        },
            Response.ErrorListener {
                error->
            error.printStackTrace()
        })
        return stringRequest
    }

    @SuppressLint("RestrictedApi")
    fun update(id:Int): JsonObjectRequest{
        // Inicializacion del Objeto JSON
        val jsonObject= JSONObject()
        jsonObject.put("Nombre",pub.Nombre)
        jsonObject.put("Edad",pub.Edad)
        jsonObject.put("Tipo",pub.Tipo)
        jsonObject.put("Descripcion",pub.Descripcion)
        jsonObject.put("imagen1",pub.imagen1)
        jsonObject.put("imagen2",pub.imagen2)
        jsonObject.put("imagen3",pub.imagen3)
        jsonObject.put("MeGusta",pub.MeGusta)
        jsonObject.put("Estatus",pub.Estatus)
        jsonObject.put("Usuario",pub.Usuario)

        //val queue = Volley.newRequestQueue(this)
        val url="https://apipublicaciones.zambiaa.com/api/publicaciones/{$id}"
        val stringRequest= JsonObjectRequest(
            Request.Method.PUT,url,jsonObject,
            Response.Listener { response ->
                Log.i(PackageManagerCompat.LOG_TAG,"Response is: $response")
            },
            Response.ErrorListener {
                    error->
                error.printStackTrace()
            })
        return stringRequest
    }

    @SuppressLint("RestrictedApi")
    fun delete(id:Int): StringRequest {
        val url="https://apipublicaciones.zambiaa.com/api/publicaciones/{$id}"
        val stringRequest= StringRequest(
            Request.Method.DELETE,url,
            Response.Listener { response ->
                Log.i(PackageManagerCompat.LOG_TAG,"Response is: $response")
            },
            Response.ErrorListener {
                    error->
                error.printStackTrace()
            })
        return stringRequest
    }

    @SuppressLint("RestrictedApi")
    fun getAll():StringRequest{
        val url="https://apipublicaciones.zambiaa.com/api/publicaciones"
        val stringRequest= StringRequest(
            Request.Method.GET,url,
            Response.Listener { response ->
                Log.i(PackageManagerCompat.LOG_TAG,"Response is: $response")
            },
            Response.ErrorListener {
                    error->
                error.printStackTrace()
            })
        return stringRequest
    }

    @SuppressLint("RestrictedApi")
    fun getById(id:Int):StringRequest{
        val url="https://apipublicaciones.zambiaa.com/api/publicaciones/{$id}"
        val stringRequest= StringRequest(
            Request.Method.GET,url,
            Response.Listener { response ->
                Log.i(PackageManagerCompat.LOG_TAG,"Response is: $response")
            },
            Response.ErrorListener {
                    error->
                error.printStackTrace()
            })
        return stringRequest
    }

    @SuppressLint("RestrictedApi")
    fun getByUser(user:String):StringRequest{
        val url="https://apipublicaciones.zambiaa.com/api/publicaciones/2/{$user}"
        val stringRequest= StringRequest(
            Request.Method.GET,url,
            Response.Listener { response ->
                Log.i(PackageManagerCompat.LOG_TAG,"Response is: $response")
            },
            Response.ErrorListener {
                    error->
                error.printStackTrace()
            })
        return stringRequest
    }

}