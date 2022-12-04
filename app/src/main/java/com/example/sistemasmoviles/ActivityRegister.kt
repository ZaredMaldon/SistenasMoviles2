@file:Suppress("DEPRECATION")

package com.example.sistemasmoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.sistemasmoviles.Controller.UsuarioController
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class ActivityRegister : AppCompatActivity() {
    private lateinit var txtName:EditText
    private lateinit var txtEmail:EditText
    private lateinit var txtApellidoP:EditText
    private lateinit var txtApellidoM:EditText
    private lateinit var txtUsuario:EditText
    private lateinit var txtContraseña:EditText
    private lateinit var txtConfirmar:EditText
    private lateinit var image:String
    private val File = 1
    //private val database = Firebase.database
    //val myRef = database.getReference("user")

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
            //LLAMAMOS A LAS VISTAS
            txtName=findViewById(R.id.TB_NombreU)
            txtEmail=findViewById(R.id.TB_EmailU)
            txtApellidoP=findViewById(R.id.TB_ApPatU)
            txtApellidoM=findViewById(R.id.TB_ApMatU)
            txtUsuario=findViewById(R.id.TB_UsuarioU)
            txtContraseña=findViewById(R.id.TB_ContraseñaU)
            txtConfirmar=findViewById(R.id.TB_ConfirmarU)

    }

    fun fileUpload() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, File)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == File) {
            if (resultCode == RESULT_OK) {
                val FileUri = data!!.data
                val Folder: StorageReference =
                    FirebaseStorage.getInstance().getReference().child("User").child("Img_Perfil_Usuario")
                val file_name: StorageReference = Folder.child("file" + FileUri!!.lastPathSegment)
                file_name.putFile(FileUri).addOnSuccessListener { taskSnapshot ->
                    file_name.getDownloadUrl().addOnSuccessListener { uri ->
                        image=java.lang.String.valueOf(uri)
                        val hashMap =
                            HashMap<String, String>()
                        hashMap["link"] = java.lang.String.valueOf(uri)
                        //myRef.setValue(hashMap)
                        Log.d("Mensaje", "Se subió correctamente")
                    }
                }
            }
        }
    }

    fun onClickRegistrar(view: View) {//Registro

        var nombreU: String = txtName.text.toString()
        var emailU: String = txtEmail.text.toString()
        var apellidoP: String = txtApellidoP.text.toString()
        var apellidoM: String = txtApellidoM.text.toString()
        var usuarioU: String = txtUsuario.text.toString()
        var contraseñaU: String = txtContraseña.text.toString()
        var confirmar: String = txtConfirmar.text.toString()


        if (!TextUtils.isEmpty(nombreU) && !TextUtils.isEmpty(emailU) && !TextUtils.isEmpty(apellidoP) && !TextUtils.isEmpty(apellidoM) && !TextUtils.isEmpty(usuarioU) && !TextUtils.isEmpty(contraseñaU)) {//revisamos que no estan vacios
            if(contraseñaU == confirmar) {
                var db = UsuarioController();
                db.createNewAccount(emailU, nombreU, apellidoP, apellidoM, usuarioU, contraseñaU,image)
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Las contraseñas son distintas", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
        }

    }

    fun onClickVolver(view: View) {
        val change = Intent(this,ActivityLogin::class.java)
        startActivity(change)

    }

    fun onClickAdjuntar(view: View) {
        fileUpload()
    }

}