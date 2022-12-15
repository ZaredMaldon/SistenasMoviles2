@file:Suppress("DEPRECATION")

package com.example.sistemasmoviles

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.toolbox.Volley
import com.example.sistemasmoviles.Controller.ImagenController
import com.example.sistemasmoviles.Controller.PublicacionController
import com.example.sistemasmoviles.Model.ImagenPubli
import com.example.sistemasmoviles.Model.Publicacion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.ImageCarousel

class ActivityEditarPublicacion : AppCompatActivity() {
    private lateinit var publicacionController:PublicacionController
    private lateinit var txtNombre: EditText
    private lateinit var txtEdad: EditText
    private lateinit var txtTipo:EditText
    private lateinit var txtDescripcion:EditText
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userFirebase: FirebaseUser
    private val fileResult = 1
    private val links = mutableListOf<String>()
    val prefs = PreferenceManager.getDefaultSharedPreferences(this)
    val idPublicacion = prefs.getInt("idPublicacionEdit",0)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editarpublicacion)

        txtNombre=findViewById(R.id.TB_NombrePM)
        txtEdad=findViewById(R.id.TB_EdadPM)
        txtTipo=findViewById(R.id.TB_TipoPM)
        txtDescripcion=findViewById(R.id.TB_DescripcionPM)

        firebaseAuth = FirebaseAuth.getInstance()
        userFirebase = firebaseAuth.currentUser!!


    }
    private fun end(){
        txtNombre.setText("")
    }

    /*Botones*/
    fun onClickVolver(view: View) {
        val change = Intent(this,ActivityPublicaciones::class.java)
        startActivity(change)
    }
    fun onClickPublicar(view: View) {

        /*DATOS DE LA PUBLICACION*/
        var publi=Publicacion(txtNombre.text.toString(),txtEdad.text.toString(),txtTipo.text.toString(),txtDescripcion.text.toString(), 20,1,userFirebase.uid)

        /*API*/
        /* Publicacion */
        PublicacionController(publi).editar(idPublicacion)

    }


}