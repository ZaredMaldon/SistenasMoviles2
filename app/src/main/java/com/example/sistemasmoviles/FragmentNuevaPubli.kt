package com.example.sistemasmoviles

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sistemasmoviles.Controller.ImagenController
import com.example.sistemasmoviles.Controller.PublicacionController
import com.example.sistemasmoviles.Model.ImagenPubli
import com.example.sistemasmoviles.Model.Publicacion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.ImageCarousel

class FragmentNuevaPubli : Fragment() {

    private val fileResult = 1
    private val links = mutableListOf<String>()

    private lateinit var txtNombre: EditText
    private lateinit var txtEdad: EditText
    private lateinit var txtTipo: EditText
    private lateinit var txtDescripcion: EditText

    private val listaCorusel = mutableListOf<CarouselItem>()
    private lateinit var carousel: ImageCarousel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nueva_publi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtNombre=view.findViewById(R.id.TB_NombrePM)
        txtEdad=view.findViewById(R.id.TB_EdadPM)
        txtTipo=view.findViewById(R.id.TB_TipoPM)
        txtDescripcion=view.findViewById(R.id.TB_DescripcionPM)
        carousel=view.findViewById(R.id.carousel)
        carousel= view.findViewById(R.id.carousel)

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
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {


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
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Publicaciones")
        val path =mUri.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/')+1))



        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->
                val hashMap = HashMap<String, String>()
                hashMap["link"] = java.lang.String.valueOf(uri)
                links.add(java.lang.String.valueOf(uri))
                listaCorusel.add(CarouselItem(java.lang.String.valueOf(uri)))
                carousel.addData(listaCorusel)
                //myRef.child(myRef.push().key.toString()).setValue(hashMap)

                Log.i("message", "file upload successfully")
            }
        }.addOnFailureListener {
            Log.i("message", "file upload error")
        }
    }

    fun onClickPublicar(view: View) {
        //Aqui poner la lista con las direcciones de las imagenes y agregarla a la tabla de imagenes
        /*DATOS DE LA PUBLICACION*/

        var firebaseAuth = FirebaseAuth.getInstance()
        var userFirebase = firebaseAuth.currentUser!!

        var publi= Publicacion(txtNombre.text.toString(),txtEdad.text.toString(),txtTipo.text.toString(),txtDescripcion.text.toString(), 20,1,userFirebase.uid)

        /*API*/
        /* Publicacion */
        var resultado= PublicacionController(publi).agregar()

        if(resultado){
            /*Imagenes*/
            for(item in links){
                var imagen= ImagenPubli(item)
                context?.let { ImagenController(imagen).agregarImagen(it) }
            }
            links.clear()
            Toast.makeText(activity, "Publicado exitosamente", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(activity, "Error: No se pudo obtener informacion", Toast.LENGTH_SHORT).show()
        }

    }

    fun onClickAdjuntar(view: View) {
        //listaCorusel.clear()
        fileManager()

    }

}