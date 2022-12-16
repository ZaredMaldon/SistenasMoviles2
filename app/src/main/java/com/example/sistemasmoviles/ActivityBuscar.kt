package com.example.sistemasmoviles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sistemasmoviles.HTTP.RestEngine
import com.example.sistemasmoviles.HTTP.Service
import com.example.sistemasmoviles.Model.Respuesta
import com.example.sistemasmoviles.adapter.PostAdapter
import com.example.sistemasmoviles.databinding.ActivityBuscarBinding
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityBuscar : AppCompatActivity(){
    private lateinit var mDataBase: DatabaseReference;
    private lateinit var search:SearchView
    var listaP = mutableListOf<Respuesta>()
    private lateinit var binding: ActivityBuscarBinding
    private lateinit var adapterUsuario: PostAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)
        search = findViewById(R.id.sv_Search)

        binding = ActivityBuscarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.svSearch.setOnQueryTextListener(this)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svSearch.clearFocus()
                Toast.makeText(this@ActivityBuscar, "Ebtre", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return false
            }

        })


        mDataBase = FirebaseDatabase.getInstance().reference;


        getPublicaciones()

    }

    private fun initRecyclerView(){
        adapterUsuario = PostAdapter(
            postsList = listaP,
            onClickListener = {
                    publicacion -> onItemSelected(publicacion)
            }
        )
        val manager = LinearLayoutManager(this@ActivityBuscar)
        binding.recyclerViewPublis.layoutManager = manager
        binding.recyclerViewPublis.adapter = adapterUsuario
    }

    private fun onItemSelected(publicacion: Respuesta) {
        val change = Intent(this,ActivityPmascota::class.java)
        change.putExtra("idPubli",publicacion.id)
        startActivity(change)
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
                listaP = (response.body() as MutableList<Respuesta>?)!!

                initRecyclerView()
                Toast.makeText(this@ActivityBuscar,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

    /*private fun getPub(query: String?) {

        //var list:MutableList<Respuesta> = arrayListOf()
        CoroutineScope(Dispatchers.IO).launch {

            val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
            val result: Response<List<Respuesta>> = service.getPublicaciones(query)
            val publis: List<Respuesta>? = result.body()
            Log.e("Lista",publis.toString())
            runOnUiThread{

                if(result.isSuccessful){
                    val pub:List<Respuesta> = publis?: emptyList()
                    listaP.clear()
                    listaP.addAll(pub)

                    adapterUsuario.notifyDataSetChanged()
                }else{
                    showError()
                }
            }

        }
    }*/

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
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
        //Toast.makeText(this, txtSearch.text.toString(), Toast.LENGTH_SHORT).show()
    }




}