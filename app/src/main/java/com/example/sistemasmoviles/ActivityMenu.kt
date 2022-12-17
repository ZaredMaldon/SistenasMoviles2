package com.example.sistemasmoviles

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Controller.UsuarioController
import com.example.sistemasmoviles.HTTP.RestEngine
import com.example.sistemasmoviles.HTTP.Service
import com.example.sistemasmoviles.Model.Respuesta
import com.example.sistemasmoviles.adapter.PostAdapter
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarMenu
import com.google.android.material.navigation.NavigationBarView
import retrofit2.Call
import retrofit2.Callback

class ActivityMenu : AppCompatActivity() {

    lateinit var bottomMenu: BottomNavigationView

    private var home: FragmentHome = FragmentHome()
    private var mensajes: FragmentMensajes = FragmentMensajes()
    private var buscar: FragmentBuscar = FragmentBuscar()
    private var usuario: FragmentUsuario = FragmentUsuario()
    private var editar: FragmentEditar = FragmentEditar()

    fun onClickVolver(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.container, home).commit()
    }

    fun onClickUsuario(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.container, usuario).commit()
    }

    fun onClickEditar(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.container, editar).commit()
    }

    fun onClickLogOut(view: View) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Seguro que quieres cerrar sesión?")
            .setCancelable(false)
            .setPositiveButton("Si") { _, _ ->

                val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                val editor: SharedPreferences.Editor = preferences.edit()
                editor.remove("UsuarioJson")
                editor.apply()
                startActivity(Intent(this,ActivityLogin::class.java))

            }
        builder.setNegativeButton("No") { DialogInterface, _ ->
                DialogInterface.cancel()
            }
        val alert: AlertDialog = builder.create()
        alert.show()

    }

    fun onClickModificar(view: View) {

        var txtName: EditText =findViewById(R.id.TB_NombreU)
        var txtEmail: EditText =findViewById(R.id.TB_EmailU)
        var txtApellidoP: EditText =findViewById(R.id.TB_ApPatU)
        var txtApellidoM: EditText =findViewById(R.id.TB_ApMatU)
        var txtUsuario: EditText =findViewById(R.id.TB_UsuarioU)
        var txtContrasenia: EditText =findViewById(R.id.TB_ContraseñaU)
        var txtConfirmar: EditText =findViewById(R.id.TB_ConfirmarU)

        var nombreU: String = txtName.text.toString()
        var emailU: String = txtEmail.text.toString()
        var apellidoP: String = txtApellidoP.text.toString()
        var apellidoM: String = txtApellidoM.text.toString()
        var usuarioU: String = txtUsuario.text.toString()
        var contraseñaU: String = txtContrasenia.text.toString()
        var confirmar: String = txtConfirmar.text.toString()

        if (!TextUtils.isEmpty(nombreU) && !TextUtils.isEmpty(emailU) && !TextUtils.isEmpty(apellidoP) && !TextUtils.isEmpty(apellidoM) && !TextUtils.isEmpty(usuarioU) && !TextUtils.isEmpty(contraseñaU)) {//revisamos que no estan vacios
            if(contraseñaU == confirmar) {
                var db = UsuarioController()
                db.modifyAccount(emailU, nombreU, apellidoP, apellidoM, usuarioU, contraseñaU)
                Toast.makeText(this, "Usuario modificado exitosamente", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Las contraseñas son distintas", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        bottomMenu = findViewById(R.id.bottom_navigation)

        supportFragmentManager.beginTransaction().replace(R.id.container, home).commit()

        bottomMenu.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, home).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.messages -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, mensajes).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.search -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, buscar).commit()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

    }

}

