package com.example.sistemasmoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.sistemasmoviles.Controller.PublicacionController
import com.example.sistemasmoviles.HTTP.RestEngine
import com.example.sistemasmoviles.HTTP.Service
import com.example.sistemasmoviles.Model.Publicacion
import com.example.sistemasmoviles.Model.Respuesta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Callback
import java.util.regex.Matcher
import java.util.regex.Pattern

class editarPublicacion : AppCompatActivity() {
    private lateinit var txtNombre: EditText
    private lateinit var txtEdad: EditText
    private lateinit var txtTipo: EditText
    private lateinit var txtDescripcion: EditText
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userFirebase: FirebaseUser
    private var idPubli = 0
    private val fileResult = 1
    var validacion = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_publicacion)

        intent.getIntExtra("idPubli", 0).let {
            idPubli = it
        }
        Toast.makeText(this, idPubli.toString(), Toast.LENGTH_SHORT).show()

        txtNombre = findViewById(R.id.TB_NombrePM)
        txtEdad = findViewById(R.id.TB_EdadPM)
        txtTipo = findViewById(R.id.TB_TipoPM)
        txtDescripcion = findViewById(R.id.TB_DescripcionPM)

        firebaseAuth = FirebaseAuth.getInstance()
        userFirebase = firebaseAuth.currentUser!!
        getPublicacion()

    }

    private fun getPublicacion() {

        val service: Service = RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<Respuesta> = service.getPublicacionesById(idPubli)

        result.enqueue(object : Callback<Respuesta> {

            override fun onFailure(call: Call<Respuesta>, t: Throwable) {
                Toast.makeText(this@editarPublicacion, t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<Respuesta>,
                response: retrofit2.Response<Respuesta>
            ) {
                val publicacion = response.body()
                if (response.isSuccessful) {
                    txtNombre.setText(publicacion!!.Nombre)
                    txtEdad.setText(publicacion.Edad)
                    txtTipo.setText(publicacion.Tipo)
                    txtDescripcion.setText(publicacion.Descripcion)
                }
            }
        })

    }


    /*Botones*/
    fun onClickVolver(view: View) {
        val change = Intent(this, ActivityMenu::class.java)
        startActivity(change)
    }

    fun onClickPublicar(view: View) {

        if (txtNombre.text.isNullOrEmpty() || txtEdad.text.isNullOrEmpty() || txtTipo.text.isNullOrEmpty() || txtDescripcion.text.isNullOrEmpty()) {
            Toast.makeText(this, "Llene todos los datos", Toast.LENGTH_SHORT).show()
            validacion = false
        }
        if (!isNumber(txtEdad.text.toString())) {
            Toast.makeText(this, "Edad debe ser un numero", Toast.LENGTH_SHORT).show()
            validacion = false
        }
        if (!isTextValid(txtNombre.text.toString())) {
            validacion = false
            Toast.makeText(this, "El nombre debe tener solo letras", Toast.LENGTH_SHORT).show()

        }
        if (!isTextValid(txtDescripcion.text.toString())) {
            validacion = false
            Toast.makeText(this, "La Descripcion debe tener solo letras", Toast.LENGTH_SHORT).show()

        }
        if (!isTextValid(txtTipo.text.toString())) {
            validacion = false
            Toast.makeText(this, "El tipo debe tener solo letras", Toast.LENGTH_SHORT).show()
        }

        /*DATOS DE LA PUBLICACION*/
        if(validacion){
            var publi = Publicacion(
            txtNombre.text.toString(),
            txtEdad.text.toString(),
            txtTipo.text.toString(),
            txtDescripcion.text.toString(),
            20,
            1,
            userFirebase.uid
        )
            /*API*/
            /* Publicacion */
            PublicacionController(publi).editar(idPubli)
            Toast.makeText(this, "La publicacion se modifico correctamente", Toast.LENGTH_SHORT).show()
            limpiar()
        }



    }

    fun limpiar() {
        txtNombre.setText("")
        txtEdad.setText("")
        txtTipo.setText("")
        txtDescripcion.setText("")
    }

    fun isNumber(s: String?): Boolean {
        return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
    }

    fun isTextValid(text: String): Boolean {
        val regEx = "^[a-zñ]+[a-zñ\\s]+[a-zñ]\$"
        val inputStr: CharSequence = text
        val pattern: Pattern = Pattern.compile(regEx, Pattern.UNICODE_CASE)
        val matcher: Matcher = pattern.matcher(inputStr)
        return if (matcher.matches()) true else false
    }
}