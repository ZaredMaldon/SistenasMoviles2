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
import android.widget.TextView
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

    private lateinit var adapterUsuario: PostAdapter

    var listaP = mutableListOf<Respuesta>()

    fun onClickVolver(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.container, home).commit()
    }

    fun onClickUsuario(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.container, usuario).commit()
    }

    fun onClickEditar(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.container, editar).commit()
    }

    fun onClickMascota(id: Int) {
        val change = Intent(this, ActivityPmascota::class.java)
        change.putExtra("idPubli", id)
        startActivity(change)
    }

    fun onClickBuscar(view: View) {//Buscar
        var tvBuscar: TextView = findViewById(R.id.tvBuscar)
        if(tvBuscar.text.isNullOrEmpty()){
            getPublicaciones()
        }else{
            getPub(tvBuscar.text.toString())
        }
    }

    private fun getPub(query: String?) {

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<Respuesta>> = service.getPublicaciones(query)

        Log.e("getPublicaciones",result.toString())

        result.enqueue(object: Callback<List<Respuesta>> {

            override fun onFailure(call: Call<List<Respuesta>>, t: Throwable){

                Toast.makeText( this@ActivityMenu,t.message.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Respuesta>>, response: retrofit2.Response<List<Respuesta>>){
                //showData(response.body()!!)
                val publis: List<Respuesta>? = response.body()
                val pub:List<Respuesta> = publis?: emptyList()
                listaP.clear()
                listaP.addAll(pub)
                adapterUsuario =
                    PostAdapter(
                        postsList = listaP,
                        onClickListener = {
                                publicacion -> onClickMascota(publicacion.id)
                        }
                    )
                adapterUsuario.notifyDataSetChanged()

                initRecyclerView()

                Toast.makeText(this@ActivityMenu,"OK",Toast.LENGTH_LONG).show()
            }
        })
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
                    getPublicaciones()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

    }

    fun getPublicaciones() {
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<Respuesta>> = service.getPublicaciones()

        Log.e("getPublicaciones",result.toString())

        result.enqueue(object: Callback<List<Respuesta>> {

            override fun onFailure(call: Call<List<Respuesta>>, t: Throwable){

                Toast.makeText( this@ActivityMenu,t.message,Toast.LENGTH_LONG).show()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Respuesta>>, response: retrofit2.Response<List<Respuesta>>){
                //showData(response.body()!!)
                val publis: List<Respuesta>? = response.body()
                val pub:List<Respuesta> = publis?: emptyList()
                listaP.clear()
                listaP.addAll(pub)
                adapterUsuario =
                    PostAdapter(
                        postsList = listaP,
                        onClickListener = {
                                publicacion -> onClickMascota(publicacion.id)
                        }
                    )
                adapterUsuario.notifyDataSetChanged()

                initRecyclerView()
                Toast.makeText( this@ActivityMenu,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initRecyclerView(){

        val recyclerViewPublis: RecyclerView = findViewById(R.id.recyclerViewPublis)
        recyclerViewPublis.layoutManager = LinearLayoutManager(this)
        recyclerViewPublis.adapter = adapterUsuario

    }

}

