package com.example.sistemasmoviles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
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
    private lateinit var mDataBase: DatabaseReference;
    private lateinit var txtSearch:TextView
    private lateinit var recyclerView:RecyclerView
    private lateinit var lista:MutableList<Respuesta>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)
        recyclerView=findViewById<RecyclerView>(R.id.recyclerViewPublis)
        txtSearch=findViewById(R.id.tv_Buscar)
        mDataBase = FirebaseDatabase.getInstance().reference;
        getPublicaciones()

    }

    fun getPublicaciones() {
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<Respuesta>> = service.getPublicaciones()
        Log.e("getPublicaciones",result.toString())
        result.enqueue(object: Callback<List<Respuesta>> {

            override fun onFailure(call: Call<List<Respuesta>>, t: Throwable){

                Toast.makeText( this@ActivityBuscar,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Respuesta>>, response: retrofit2.Response<List<Respuesta>>){

                //showData(response.body()!!)
                lista = (response.body() as MutableList<Respuesta>?)!!
                showData(lista)
                Toast.makeText(this@ActivityBuscar,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }
    fun getBusqueda(){
        //var busqueda = Busqueda(txtSearch.text.toString())
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<Respuesta>> = service.getPublicaciones(txtSearch.text.toString())

        result.enqueue(object: Callback<List<Respuesta>> {

            override fun onFailure(call: Call<List<Respuesta>>, t: Throwable){

                Toast.makeText( this@ActivityBuscar,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Respuesta>>, response: retrofit2.Response<List<Respuesta>>){

                refresh(response.body()!!)

                Toast.makeText(this@ActivityBuscar,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showData(publi:List<Respuesta>){
            recyclerView.apply{
                layoutManager = LinearLayoutManager(this@ActivityBuscar)
                adapter = PostAdapter(publi)

            }
    }

    private fun refresh(publi:List<Respuesta>){
        recyclerView.apply{
            layoutManager = LinearLayoutManager(this@ActivityBuscar)
            adapter = PostAdapter(publi)

        }
        recyclerView.adapter!!.notifyDataSetChanged()
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
        //getPublicaciones()
        recyclerView.layoutManager!!.removeAllViews()
        getBusqueda()
        //Toast.makeText(this, txtSearch.text.toString(), Toast.LENGTH_SHORT).show()
    }
}