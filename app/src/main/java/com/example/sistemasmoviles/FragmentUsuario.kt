package com.example.sistemasmoviles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.HTTP.RestEngine
import com.example.sistemasmoviles.HTTP.Service
import com.example.sistemasmoviles.Model.Respuesta
import com.example.sistemasmoviles.adapter.PostAdapter
import com.example.sistemasmoviles.adapter.PostAdapterUsuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class FragmentUsuario : Fragment() {

    var listaP:MutableList<Respuesta> = arrayListOf()

    private lateinit var adapterUsuario: PostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var textNombre: TextView = view.findViewById(R.id.id_NombreU)
        var textCorreo: TextView = view.findViewById(R.id.id_EmailU)
        var imageview: ImageView = view.findViewById(R.id.iv_usuarioImage)

        var firebaseAuth = FirebaseAuth.getInstance()
        var userFirebase = firebaseAuth.currentUser!!

        var mDataBase = FirebaseDatabase.getInstance().getReference("User")

        mDataBase.child(userFirebase.uid).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var nombre = snapshot.child("Name").value.toString()
                    var correo = snapshot.child("Email").value.toString()
                    var urlImg = snapshot.child("image").value.toString()

                    textNombre.text = nombre
                    textCorreo.text = correo

                    Picasso.with(activity)
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
                initRecyclerView(view)
            }
        }
    }

    fun getPublicacionesByUser(): MutableList<Respuesta> {

        var firebaseAuth = FirebaseAuth.getInstance()
        var userFirebase = firebaseAuth.currentUser!!

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

    private fun initRecyclerView(view: View){
        var adapterUsuario = PostAdapterUsuario(
            postsList = listaP,
            onClickListener = {publicacion -> onItemSelected(publicacion)},
            onClickDelete = {position -> onDeletedItem(position)},
            onClickUpdate = {publicacion -> onUpdateItem(publicacion)},
            getId = {id -> deleteById(id)}
        )
        val manager = LinearLayoutManager(activity)

        var rvPublicacionesU: RecyclerView = view.findViewById(R.id.rvPublicacionesU)
        rvPublicacionesU.layoutManager = manager
        rvPublicacionesU.adapter = adapterUsuario
    }

    private fun onUpdateItem(publicacion:Respuesta) {
        val change = Intent(activity,editarPublicacion::class.java)
        change.putExtra("idPubli",publicacion.id)
        startActivity(change)
    }

    private fun deleteById(id: Int) {
        //PublicacionController(Publicacion("null","null","null","null",0,0,"null")).eliminar(id)
        val service: Service = RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<Void> = service.deletePublicacion(id)
        result.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.e("Delete: ", response.code().toString())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Delete: ", t.toString())
            }

        })


        Toast.makeText(activity, "Se la elimino publicacion id: "+id.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun onDeletedItem(position: Int) {
        //remover item de la lista
        listaP.removeAt(position)
        adapterUsuario.notifyItemRemoved(position)

    }

    private fun onItemSelected(respuesta:Respuesta) {
        //cuando selecciona
        val change = Intent(activity,ActivityPmascota::class.java)
        change.putExtra("idPubli",respuesta.id)
        startActivity(change)
    }

}