package com.example.sistemasmoviles

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import android.net.Uri
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
import androidx.constraintlayout.helper.widget.Carousel
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Controller.ImagenController
import com.example.sistemasmoviles.Controller.PublicacionController
import com.example.sistemasmoviles.Controller.UsuarioController
import com.example.sistemasmoviles.HTTP.RestEngine
import com.example.sistemasmoviles.HTTP.Service
import com.example.sistemasmoviles.Model.ImagenPubli
import com.example.sistemasmoviles.Model.Publicacion
import com.example.sistemasmoviles.Model.Respuesta
import com.example.sistemasmoviles.adapter.PostAdapter
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarMenu
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import retrofit2.Call
import retrofit2.Callback

class ActivityMenu : AppCompatActivity() {

    lateinit var bottomMenu: BottomNavigationView

    private var home: FragmentHome = FragmentHome()
    private var mensajes: FragmentMensajes = FragmentMensajes()
    private var buscar: FragmentBuscar = FragmentBuscar()
    private var usuario: FragmentUsuario = FragmentUsuario()
    private var editar: FragmentEditar = FragmentEditar()
    private var nuevaPubli: FragmentNuevaPubli = FragmentNuevaPubli()

    private lateinit var adapterUsuario: PostAdapter

    var listaP = mutableListOf<Respuesta>()

    val links = mutableListOf<String>()

    private val fileResult = 1

    fun onClickVolver(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.container, home).commit()
    }

    fun onClickUsuario(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.container, usuario).commit()
    }

    fun onClickEditar(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.container, editar).commit()
    }

    fun onClickNuevaPubli(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.container, nuevaPubli).commit()
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

    fun onClickAdjuntar(view: View) {

        fileManager()

    }

    fun onClickPublicar(view: View) {
        //Aqui poner la lista con las direcciones de las imagenes y agregarla a la tabla de imagenes
        /*DATOS DE LA PUBLICACION*/

        var firebaseAuth = FirebaseAuth.getInstance()
        var userFirebase = firebaseAuth.currentUser!!

        var txtNombre: EditText= findViewById(R.id.TB_NombrePM)
        var txtEdad : EditText=findViewById(R.id.TB_EdadPM)
        var txtTipo : EditText=findViewById(R.id.TB_TipoPM)
        var txtDescripcion : EditText=findViewById(R.id.TB_DescripcionPM)

        /*API*/
        /* Publicacion */

        var publi=Publicacion(txtNombre.text.toString(),txtEdad.text.toString(),txtTipo.text.toString(),txtDescripcion.text.toString(), 20,1,userFirebase.uid)
        Log.e("Links",links.toString())
        PublicacionController(publi).agregar(links,this@ActivityMenu)
        Toast.makeText(this, "Publicado exitosamente", Toast.LENGTH_SHORT).show()

        txtNombre.setText("")
        txtEdad.setText("")
        txtTipo.setText("")
        txtDescripcion.setText("")
        supportFragmentManager.beginTransaction().replace(R.id.container, home).commit()

    }

    private fun fileManager() {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        intent.type = "*/*"
        startActivityForResult(intent, fileResult)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK && data != null) {


                val clipData = data.clipData

                if (clipData != null){
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        uri?.let { fileUpload(it) }
                    }

                }else {
                    val uri = data.data
                    uri?.let { fileUpload(it) }
                }

            }
        }
    }

    private fun fileUpload(mUri: Uri) {
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Publicaciones")
        val path =mUri.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/')+1))

        val listaCorusel = mutableListOf<CarouselItem>()
        val carousel: ImageCarousel = findViewById(R.id.carousel)

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->
                val hashMap = HashMap<String, String>()
                hashMap["link"] = java.lang.String.valueOf(uri)
                links.add(java.lang.String.valueOf(uri))
                listaCorusel.add(CarouselItem(java.lang.String.valueOf(uri)))
                carousel.addData(listaCorusel)
                //myRef.child(myRef.push().key.toString()).setValue(hashMap)

                Log.i("message", "file upload successfully")
            }
        }.addOnFailureListener {
            Log.i("message", "file upload error")
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

                //Toast.makeText(this@ActivityMenu,"OK",Toast.LENGTH_LONG).show()
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
                //Toast.makeText( this@ActivityMenu,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initRecyclerView(){

        val recyclerViewPublis: RecyclerView = findViewById(R.id.recyclerViewPublis)
        recyclerViewPublis.layoutManager = LinearLayoutManager(this)
        recyclerViewPublis.adapter = adapterUsuario

    }

}

