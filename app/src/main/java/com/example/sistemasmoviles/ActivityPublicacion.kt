@file:Suppress("DEPRECATION")

package com.example.sistemasmoviles

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.android.volley.toolbox.Volley
import com.example.sistemasmoviles.Controller.PublicacionController
import com.example.sistemasmoviles.Model.Publicacion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ActivityPublicacion : AppCompatActivity() {
    private lateinit var publicacionController:PublicacionController
    private lateinit var txtNombre: EditText
    private lateinit var txtEdad: EditText
    private lateinit var txtTipo:EditText
    private lateinit var txtDescripcion:EditText
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userFirebase: FirebaseUser
    private val fileResult = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicacion)

        txtNombre=findViewById(R.id.TB_NombrePM)
        txtEdad=findViewById(R.id.TB_EdadPM)
        txtTipo=findViewById(R.id.TB_TipoPM)
        txtDescripcion=findViewById(R.id.TB_DescripcionPM)

        firebaseAuth = FirebaseAuth.getInstance()
        userFirebase = firebaseAuth.currentUser!!

    }
    private fun end(){
        txtNombre.setText("")
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
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("User")
        val path =mUri.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/')+1))

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->
                val hashMap = HashMap<String, String>()
                hashMap["link"] = java.lang.String.valueOf(uri)

                //myRef.child(myRef.push().key.toString()).setValue(hashMap)

                Log.i("message", "file upload successfully")
            }
        }.addOnFailureListener {
            Log.i("message", "file upload error")
        }
    }

    /*Botones*/
    fun onClickVolver(view: View) {
        val change = Intent(this,ActivityPublicaciones::class.java)
        startActivity(change)
    }
    fun onClickPublicar(view: View) {
        /*DATOS DE LA PUBLICACION*/
        var publi=Publicacion(txtNombre.text.toString(),txtEdad.text.toString(),txtTipo.text.toString(),txtDescripcion.text.toString(),
            null,null,null,20,1,userFirebase.uid)
        /*API*/
        val queue = Volley.newRequestQueue(this)
        publicacionController= PublicacionController(publi)
        var stringRequest=publicacionController.enviar()
        queue.add(stringRequest)
        end()
    }

    fun onClickAdjuntar(view: View) {
        //fileManager()
    }


}