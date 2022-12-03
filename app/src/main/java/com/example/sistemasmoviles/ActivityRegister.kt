package com.example.sistemasmoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.sistemasmoviles.Controller.UsuarioController

class ActivityRegister : AppCompatActivity() {
    private lateinit var txtName:EditText
    private lateinit var txtEmail:EditText
    private lateinit var txtApellidoP:EditText
    private lateinit var txtApellidoM:EditText
    private lateinit var txtUsuario:EditText
    private lateinit var txtContraseña:EditText
    private lateinit var txtConfirmar:EditText

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //LLAMAMOS A LAS VISTAS
        txtName=findViewById(R.id.TB_NombreU)
        txtEmail=findViewById(R.id.TB_EmailU)
        txtApellidoP=findViewById(R.id.TB_ApPatU)
        txtApellidoM=findViewById(R.id.TB_ApMatU)
        txtUsuario=findViewById(R.id.TB_UsuarioU)
        txtContraseña=findViewById(R.id.TB_ContraseñaU)
        txtConfirmar=findViewById(R.id.TB_ConfirmarU)

    }

    fun onClickRegistrar(view: View) {//Registro

        var nombreU: String = txtName.text.toString()
        var emailU: String = txtEmail.text.toString()
        var apellidoP: String = txtApellidoP.text.toString()
        var apellidoM: String = txtApellidoM.text.toString()
        var usuarioU: String = txtUsuario.text.toString()
        var contraseñaU: String = txtContraseña.text.toString()
        var confirmar: String = txtConfirmar.text.toString()


        if (!TextUtils.isEmpty(nombreU) && !TextUtils.isEmpty(emailU) && !TextUtils.isEmpty(apellidoP) && !TextUtils.isEmpty(apellidoM) && !TextUtils.isEmpty(usuarioU) && !TextUtils.isEmpty(contraseñaU)) {//revisamos que no estan vacios
            if(contraseñaU == confirmar) {
                var db = UsuarioController();
                db.createNewAccount(emailU, nombreU, apellidoP, apellidoM, usuarioU, contraseñaU)
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
        val change = Intent(this,ActivityLogin::class.java)
        startActivity(change)

    }

}