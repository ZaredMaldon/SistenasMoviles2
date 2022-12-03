package com.example.sistemasmoviles.Controller

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sistemasmoviles.ActivityLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UsuarioController() : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbReference:DatabaseReference
    private lateinit var database: FirebaseDatabase

    fun createNewAccount(
        email: String,
        nombre: String,
        apPat: String,
        apMat: String,
        usuario: String,
        contraseña: String
    ) {

        database= FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()
        dbReference=database.reference.child("User")

        auth.createUserWithEmailAndPassword(email, contraseña)
            .addOnCompleteListener(this) { task ->
                if (task.isComplete) {
                    val user: FirebaseUser? = auth.currentUser
                    verifyEmail(user)
                    val userBD = dbReference.child(user?.uid.toString())
                    userBD.child("Email").setValue(email)
                    userBD.child("Name").setValue(nombre)
                    userBD.child("ApellidoPaterno").setValue(apPat)
                    userBD.child("ApellidoMaterno").setValue(apMat)
                    userBD.child("Usuario").setValue(usuario)
                    userBD.child("Password").setValue(contraseña)
                    action()
                }
            }

    }

    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                    task->
                if(task.isComplete){
                    Toast.makeText(this,"Email enviado", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Error al enviar el email", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun action(){
        startActivity(Intent(this, ActivityLogin::class.java))
    }
}
