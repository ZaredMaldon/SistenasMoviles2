package com.example.sistemasmoviles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Controller.ChatController
import com.example.sistemasmoviles.HTTP.RestEngine
import com.example.sistemasmoviles.HTTP.Service
import com.example.sistemasmoviles.Model.Respuesta
import com.example.sistemasmoviles.adapter.PostAdapter
import com.example.sistemasmoviles.databinding.ActivityBuscarBinding
import retrofit2.Call
import retrofit2.Callback

class FragmentBuscar : Fragment() {

    var listaP = mutableListOf<Respuesta>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var search: SearchView = view.findViewById(R.id.sv_Search)

        //binding.svSearch.setOnQueryTextListener(this)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                search.clearFocus()
                Toast.makeText(activity, "Ebtre", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        getPublicaciones(view)

    }

    fun getPublicaciones(view: View) {
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<Respuesta>> = service.getPublicaciones()

        Log.e("getPublicaciones",result.toString())

        result.enqueue(object: Callback<List<Respuesta>> {

            override fun onFailure(call: Call<List<Respuesta>>, t: Throwable){

                Toast.makeText( activity,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<Respuesta>>, response: retrofit2.Response<List<Respuesta>>){
                //showData(response.body()!!)
                listaP = (response.body() as MutableList<Respuesta>?)!!

                initRecyclerView(view)
                Toast.makeText(activity,"OK",Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar, container, false)
    }

    private fun initRecyclerView(view: View){

        val recyclerViewPublis: RecyclerView = view.findViewById(R.id.recyclerViewPublis)
        recyclerViewPublis.layoutManager = LinearLayoutManager(activity)
        recyclerViewPublis.adapter =
            PostAdapter(
                postsList = listaP,
                onClickListener = {
                        publicacion -> onItemSelected(publicacion)
                }
            )
    }

    private fun onItemSelected(publicacion: Respuesta) {

        val change = Intent(activity, ActivityPmascota::class.java)
        change.putExtra("idPubli",publicacion.id)
        startActivity(change)

    }

}