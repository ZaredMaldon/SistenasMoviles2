package com.example.sistemasmoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Controller.PublicacionController
import com.example.sistemasmoviles.HTTP.RestEngine
import com.example.sistemasmoviles.HTTP.Service
import com.example.sistemasmoviles.Model.Publicacion
import com.example.sistemasmoviles.Model.Respuesta
import com.example.sistemasmoviles.adapter.PostAdapter
import com.example.sistemasmoviles.adapter.PostAdapterUsuario
import com.example.sistemasmoviles.databinding.ActivityPusuarioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class ActivityPusuario : AppCompatActivity() {

    private lateinit var textNombre: TextView
    private lateinit var textCorreo: TextView
    private lateinit var imageview: ImageView

    var listaP:MutableList<Respuesta> = arrayListOf()
    private lateinit var binding: ActivityPusuarioBinding
    private lateinit var adapterUsuario: PostAdapterUsuario

    private lateinit var firebaseAuth: FirebaseAuth;
    private lateinit var userFirebase: FirebaseUser;

    private lateinit var mDataBase: DatabaseReference;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pusuario)

        binding = ActivityPusuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)


        textNombre = findViewById(R.id.id_NombreU)
        textCorreo = findViewById(R.id.id_EmailU)
        imageview = findViewById(R.id.iv_usuarioImage)

        firebaseAuth = FirebaseAuth.getInstance()
        userFirebase = firebaseAuth.currentUser!!

        mDataBase = FirebaseDatabase.getInstance().getReference("User")

        mDataBase.child(userFirebase.uid).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var nombre = snapshot.child("Name").value.toString()
                    var correo = snapshot.child("Email").value.toString()
                    var urlImg = snapshot.child("image").value.toString()

                    textNombre.text = nombre;
                    textCorreo.text = correo;

                    Picasso.with(this@ActivityPusuario)
                        .load(urlImg)
                        .error(R.mipmap.ic_launcher)
                        .fit()
                        .centerInside()
                        .into(imageview)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        runBlocking {
            val result = withContext(Dispatchers.IO){
                listaP=getPublicacionesByUser()
                initRecyclerView()
                }
        }




    }

    fun getPublicacionesByUser(): MutableList<Respuesta> {
        var list:MutableList<Respuesta> = arrayListOf()
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<Respuesta>> = service.getPublicacionByUser(userFirebase.uid)

        try {
            //for (resp in result.execute().body()!!)
           // Log.e("Respuesta", resp.toString())
            list= result.execute().body() as MutableList<Respuesta>

        }catch (e: IOException){
            Log.e("Excepcion:",e.message.toString())
        }

        return list

    }

    private fun initRecyclerView(){
        adapterUsuario = PostAdapterUsuario(
            postsList = listaP,
            onClickListener = {publicacion -> onItemSelected(publicacion)},
            onClickDelete = {position -> onDeletedItem(position)},
            onClickUpdate = {position -> onUpdateItem(position)},
            getId = {id -> deleteById(id)}
        )
        val manager = LinearLayoutManager(this@ActivityPusuario)
        binding.rvPublicacionesU.layoutManager = manager
        binding.rvPublicacionesU.adapter = adapterUsuario
    }

    private fun onUpdateItem(position: Int) {
        Log.i("Posicion:",position.toString())
        /*val change = Intent(this,ActivityEditarPublicacion::class.java)
        startActivity(change)*/
    }

    private fun deleteById(id: Int) {
        //PublicacionController(Publicacion("null","null","null","null",0,0,"null")).eliminar(id)
        val service: Service = RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<Void> = service.deletePublicacion(id)
        result.enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.e("Delete: ", response.code().toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Delete: ", t.toString())
            }

        })


        Toast.makeText(this, "id: "+id.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun onDeletedItem(position: Int) {
        //remover item de la lista
        listaP.removeAt(position)

        adapterUsuario.notifyItemRemoved(position)

    }

    private fun onItemSelected(respuesta:Respuesta) {
        //cuando selecciona
        val change = Intent(this,ActivityPmascota::class.java)
        change.putExtra("idPubli",respuesta.id)
        startActivity(change)
    }

    //Botones
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