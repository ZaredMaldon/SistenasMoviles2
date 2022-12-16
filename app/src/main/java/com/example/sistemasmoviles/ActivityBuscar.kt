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
    var listaP = mutableListOf<Respuesta>()
    private lateinit var binding: ActivityBuscarBinding
    private lateinit var adapterUsuario: PostAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)

        binding = ActivityBuscarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.svSearch.setOnQueryTextListener(this)

        mDataBase = FirebaseDatabase.getInstance().reference;
        initRecyclerView()
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

                Toast.makeText( this@ActivityBuscar,t.message.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Respuesta>>, response: retrofit2.Response<List<Respuesta>>){
                //showData(response.body()!!)
                val publis: List<Respuesta>? = response.body()
                val pub:List<Respuesta> = publis?: emptyList()
                listaP.clear()
                listaP.addAll(pub)
                adapterUsuario.notifyDataSetChanged()

                Toast.makeText(this@ActivityBuscar,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getPub(query: String?) {

        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<Respuesta>> = service.getPublicaciones(query)

        Log.e("getPublicaciones",result.toString())

        result.enqueue(object: Callback<List<Respuesta>> {

            override fun onFailure(call: Call<List<Respuesta>>, t: Throwable){

                Toast.makeText( this@ActivityBuscar,t.message.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Respuesta>>, response: retrofit2.Response<List<Respuesta>>){
                //showData(response.body()!!)
                val publis: List<Respuesta>? = response.body()
                val pub:List<Respuesta> = publis?: emptyList()
                listaP.clear()
                listaP.addAll(pub)
                adapterUsuario.notifyDataSetChanged()

                Toast.makeText(this@ActivityBuscar,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

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
        if(binding.tvBuscar.text.isNullOrEmpty()){
            getPublicaciones()
        }else{
            Log.e("e",binding.tvBuscar.text.toString())
            getPub(binding.tvBuscar.text.toString())
        }
    }

}