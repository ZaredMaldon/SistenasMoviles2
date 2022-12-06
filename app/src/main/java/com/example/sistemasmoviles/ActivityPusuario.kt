package com.example.sistemasmoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ActivityPusuario : AppCompatActivity() {

    private lateinit var textNombre: TextView
    private lateinit var textCorreo: TextView

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userFirebase: FirebaseUser

    private lateinit var mDataBase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pusuario)

        textNombre = findViewById(R.id.id_NombreU)
        textCorreo = findViewById(R.id.id_EmailU)

        firebaseAuth = FirebaseAuth.getInstance()
        userFirebase = firebaseAuth.currentUser!!

        mDataBase = FirebaseDatabase.getInstance().getReference("User")

        mDataBase.child(userFirebase.uid).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var nombre = snapshot.child("Name").value.toString()
                    var correo = snapshot.child("Email").value.toString()

                    textNombre.text = nombre
                    textCorreo.text = correo

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    fun onClickVolver(view: View) {
        val change = Intent(this,ActivityPublicaciones::class.java)
        startActivity(change)
    }

    fun onClickHome(view: View) {
        val change = Intent(this,ActivityPublicaciones::class.java)
        startActivity(change)
    }

    fun onClickMensajes(view: View) {
        val change = Intent(this,ActivityChats::class.java)
        startActivity(change)
    }

    fun onClickBuscar(view: View) {
        val change = Intent(this,ActivityBuscar::class.java)
        startActivity(change)
    }

    fun onClickEditar(view: View) {
        val change = Intent(this,ActivityModificarU::class.java)
        startActivity(change)
    }
}