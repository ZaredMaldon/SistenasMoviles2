package com.example.sistemasmoviles

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Controller.PublicacionController
import com.example.sistemasmoviles.HTTP.RestEngine
import com.example.sistemasmoviles.HTTP.Service
import com.example.sistemasmoviles.Model.Publicacion
import com.example.sistemasmoviles.Model.Respuesta
import com.example.sistemasmoviles.adapter.PostAdapter
import com.google.firebase.database.*
import retrofit2.Call
import retrofit2.Callback

class ActivityBuscar : AppCompatActivity() {
    private lateinit var publicacionController:PublicacionController
    private var mAdapter: PublicacionController? = null;
    private lateinit var mRecyclerView: RecyclerView;
    //private var mPublicacionesList = ArrayList<Publicacion>();
    private lateinit var mDataBase: DatabaseReference;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)

        mDataBase = FirebaseDatabase.getInstance().reference;
        getPublicaciones()
       // mRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewPublis);
       // mRecyclerView.layoutManager = LinearLayoutManager(this)
        //publicacionController=PublicacionController(null)



    }

    fun getPublicaciones() {
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<Respuesta>> = service.getPublicaciones()

        result.enqueue(object: Callback<List<Respuesta>> {

            override fun onFailure(call: Call<List<Respuesta>>, t: Throwable){

                Toast.makeText( this@ActivityBuscar,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Respuesta>>, response: retrofit2.Response<List<Respuesta>>){

                showData(response.body()!!)
                Toast.makeText(this@ActivityBuscar,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showData(publi:List<Respuesta>){
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerViewPublis)
        /*
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter = PostAdapter(getPublicaciones())*/
        Toast.makeText(this@ActivityBuscar,"ENTRE",Toast.LENGTH_LONG).show()
        recyclerView.apply{
            layoutManager = LinearLayoutManager(this@ActivityBuscar)
            adapter = PostAdapter(publi)

        }
    }

    fun onClickHome(view: View) {
        val change = Intent(this,ActivityPublicaciones::class.java)
        startActivity(change)
    }

    fun onClickPublicacion(view: View) {
        val change = Intent(this,ActivityPmascota::class.java)
        startActivity(change)
    }

    fun onClickMensajes(view: View) {
        val change = Intent(this,ActivityChats::class.java)
        startActivity(change)
    }

    fun onClickBuscar(view: View) {//Buscar
        //getPublicaciones(publicacionController.getAll(this)!!);
    }
}