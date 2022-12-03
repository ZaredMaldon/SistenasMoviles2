package com.example.sistemasmoviles

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sistemasmoviles.Controller.PublicacionController
import com.example.sistemasmoviles.Model.Publicacion
import com.google.firebase.database.*

class ActivityBuscar : AppCompatActivity() {

    private var mAdapter: PublicacionController? = null;
    private lateinit var mRecyclerView: RecyclerView;
    private var mPublicacionesList = ArrayList<Publicacion>();
    private lateinit var mDataBase: DatabaseReference;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)

        mDataBase = FirebaseDatabase.getInstance().reference;

        mRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewPublis);
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        getPublicacionesFromFirebase();

    }

    private fun getPublicacionesFromFirebase() {
        mDataBase.child("User").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    val children = snapshot.children

                    children.forEach {
                        var nombre: String = it.child("Name").value.toString();
                        mPublicacionesList.add(Publicacion(nombre));
                    }

                    mAdapter = PublicacionController(mPublicacionesList, R.layout.publicacion_view);
                    mRecyclerView.adapter = mAdapter;

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
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

    fun onClickBuscar(view: View) {
        val change = Intent(this,ActivityBuscar::class.java)
        startActivity(change)
    }
}