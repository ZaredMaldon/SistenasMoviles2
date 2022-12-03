package com.example.sistemasmoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.android.volley.toolbox.Volley
import com.example.sistemasmoviles.Controller.PublicacionController
import com.example.sistemasmoviles.Model.Publicacion

class ActivityPublicacion : AppCompatActivity() {
    private lateinit var publicacionController:PublicacionController
    private lateinit var txtNombre: EditText
    private lateinit var txtEdad: EditText
    private lateinit var txtTipo:EditText
    private lateinit var txtDescripcion:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicacion)

        txtNombre=findViewById(R.id.TB_NombrePM)
        txtEdad=findViewById(R.id.TB_EdadPM)
        txtTipo=findViewById(R.id.TB_TipoPM)
        txtDescripcion=findViewById(R.id.TB_DescripcionPM)

    }

    /*Botones*/
    fun onClickVolver(view: View) {
        val change = Intent(this,ActivityPublicaciones::class.java)
        startActivity(change)
    }
    fun onClickPublicar(view: View) {
        /*DATOS DE LA PUBLICACION*/
        var publi=Publicacion(txtNombre.text.toString(),txtEdad.text.toString(),txtTipo.text.toString(),txtDescripcion.text.toString(),
            null,null,null,20,1,"YUjqLkvgv0hWUyyeyzsrtShaTSH2")//Agregar el usuario que este en la sesion e imagenes
        /*API*/
        val queue = Volley.newRequestQueue(this)
        publicacionController= PublicacionController(publi)
        var stringRequest=publicacionController.enviar()
        queue.add(stringRequest)
    }

}