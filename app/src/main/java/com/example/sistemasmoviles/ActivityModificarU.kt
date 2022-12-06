package com.example.sistemasmoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.sistemasmoviles.Controller.UsuarioController
import com.example.sistemasmoviles.Model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ActivityModificarU : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtApellidoP: EditText
    private lateinit var txtApellidoM: EditText
    private lateinit var txtUsuario: EditText
    private lateinit var txtContraseña: EditText
    private lateinit var txtConfirmar: EditText

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userFirebase: FirebaseUser

    private lateinit var mDataBase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_u)

        txtName = findViewById(R.id.TB_NombreU)
        txtEmail=findViewById(R.id.TB_EmailU)
        txtApellidoP=findViewById(R.id.TB_ApPatU)
        txtApellidoM=findViewById(R.id.TB_ApMatU)
        txtUsuario=findViewById(R.id.TB_UsuarioU)
        txtContraseña=findViewById(R.id.TB_ContraseñaU)
        txtConfirmar=findViewById(R.id.TB_ConfirmarU)

        firebaseAuth = FirebaseAuth.getInstance()
        userFirebase = firebaseAuth.currentUser!!

        mDataBase = FirebaseDatabase.getInstance().getReference("User")

        mDataBase.child(userFirebase.uid).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var nombre = snapshot.child("Name").value.toString()
                    var apellidoP = snapshot.child("ApellidoPaterno").value.toString()
                    var apellidoM = snapshot.child("ApellidoMaterno").value.toString()
                    var email = snapshot.child("Email").value.toString()
                    var usuario = snapshot.child("Usuario").value.toString()
                    var contraseña = snapshot.child("Password").value.toString()

                    txtName.setText(nombre)
                    txtEmail.setText(email)
                    txtApellidoP.setText(apellidoP)
                    txtApellidoM.setText(apellidoM)
                    txtUsuario.setText(usuario)
                    txtContraseña.setText(contraseña)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun onClickModificar(view: View) {

        var nombreU: String = txtName.text.toString()
        var emailU: String = txtEmail.text.toString()
        var apellidoP: String = txtApellidoP.text.toString()
        var apellidoM: String = txtApellidoM.text.toString()
        var usuarioU: String = txtUsuario.text.toString()
        var contraseñaU: String = txtContraseña.text.toString()
        var confirmar: String = txtConfirmar.text.toString()

        if (!TextUtils.isEmpty(nombreU) && !TextUtils.isEmpty(emailU) && !TextUtils.isEmpty(apellidoP) && !TextUtils.isEmpty(apellidoM) && !TextUtils.isEmpty(usuarioU) && !TextUtils.isEmpty(contraseñaU)) {//revisamos que no estan vacios
            if(contraseñaU == confirmar) {
                var db = UsuarioController()
                db.modifyAccount(emailU, nombreU, apellidoP, apellidoM, usuarioU, contraseñaU)
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Las contraseñas son distintas", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
        }

    }

    fun onClickVolver(view: View) {
        val change = Intent(this,ActivityPusuario::class.java)
        startActivity(change)

    }
}