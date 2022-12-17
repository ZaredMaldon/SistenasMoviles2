package com.example.sistemasmoviles

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.sistemasmoviles.HTTP.RestEngine
import com.example.sistemasmoviles.HTTP.Service
import com.example.sistemasmoviles.Model.Chat
import com.example.sistemasmoviles.Model.Respuesta
import com.example.sistemasmoviles.Model.RespuestaImagen
import com.example.sistemasmoviles.databinding.ActivityBuscarBinding
import com.example.sistemasmoviles.databinding.ActivityPmascotaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class ActivityPmascota : AppCompatActivity() {

    private var idPubli=0
    private var idUsuario= ""
    private lateinit var binding: ActivityPmascotaBinding
    private val listaCorusel = mutableListOf<CarouselItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pmascota)

        binding = ActivityPmascotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

       /* intent.getStringExtra("idPubli")?.let {
            idPubli=it
        }*/
        intent.getIntExtra("idPubli",0).let {
            idPubli=it
        }

        Toast.makeText(this, idPubli.toString(), Toast.LENGTH_SHORT).show()
        getPublicacion()
    }

    private fun getPublicacion() {
        //Toast.makeText(this, idPubli, Toast.LENGTH_SHORT).show()
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<Respuesta> = service.getPublicacionesById(idPubli)

        result.enqueue(object: Callback<Respuesta> {

            override fun onFailure(call: Call<Respuesta>, t: Throwable){

                Toast.makeText( this@ActivityPmascota,"Error",Toast.LENGTH_LONG).show()
                Log.e("ErrorGetPubli: ",t.message.toString())
            }

            override fun onResponse(call: Call<Respuesta>, response: retrofit2.Response<Respuesta>){
                val publicacion = response.body()
                if(response.isSuccessful){
                    binding.tvNombreP.text = publicacion?.Nombre
                    binding.tvTipoP.text = publicacion?.Tipo
                    binding.tvDescripcionP.text = publicacion?.Descripcion
                    idUsuario = publicacion?.Usuario.toString()
                    getImages()

                    Toast.makeText(this@ActivityPmascota,publicacion?.Nombre,Toast.LENGTH_LONG).show()
                }

            }
        })

    }

    private fun getImages() {
        //Toast.makeText(this, idPubli, Toast.LENGTH_SHORT).show()
        val service: Service =  RestEngine.getRestEngine().create(Service::class.java)
        val result: Call<List<RespuestaImagen>> = service.getPublicacionImages(idPubli)

        result.enqueue(object: Callback<List<RespuestaImagen>> {

            override fun onFailure(call: Call<List<RespuestaImagen>>, t: Throwable){

                Toast.makeText( this@ActivityPmascota,"Error",Toast.LENGTH_LONG).show()
                Log.e("ErrorGetPubli: ",t.message.toString())
            }

            override fun onResponse(call: Call<List<RespuestaImagen>>, response: retrofit2.Response<List<RespuestaImagen>>){
                val imagenes = response.body()
                var list = mutableListOf<String>()
                if(response.isSuccessful){
                    var i:Int=0
                    for (imagen in imagenes!!){
                        if(i==0){
                            Picasso.with(this@ActivityPmascota)
                                .load(imagen.Url)
                                .error(R.mipmap.ic_launcher)
                                .fit()
                                .centerInside()
                                .into(binding.idPerfilUsuario2)
                        }else{
                            listaCorusel.add(CarouselItem(imagen.Url))
                        }
                        i++
                    }
                    binding.carouselP.addData(listaCorusel)
                }

            }
        })

    }

    fun onClickVolver(view: View) {
        val change = Intent(this,ActivityBuscar::class.java)
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

    fun onClickAdopcion(view: View) {

        var user = ""

        var db = Firebase.firestore

        lateinit var otherUser: String;

        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val pref: String = preferences.getString("UsuarioJson", "")!!
        if(pref != "") {

            val objPref = JSONObject(pref)
            user = objPref.getString("correo")
        }

        val chatId = UUID.randomUUID().toString()
        val users = listOf(user, otherUser)

        val chat = Chat(
            id = chatId,
            name = "Chat con $otherUser",
            users = users
        )

        db.collection("chats").document(chatId).set(chat)
        db.collection("users").document(user).collection("chats").document(chatId).set(chat)
        db.collection("users").document(otherUser).collection("chats").document(chatId).set(chat)

        val intent = Intent(this, ActivityChat::class.java)
        intent.putExtra("chatId", chatId)
        intent.putExtra("user", user)
        startActivity(intent)

    }

}