package com.example.sistemasmoviles

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sistemasmoviles.Controller.UsuarioController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.json.JSONObject
import java.util.regex.Matcher
import java.util.regex.Pattern


class ActivityLogin : AppCompatActivity() {

    private var login: LoginFragment = LoginFragment()
    private var register: RegisterFragment = RegisterFragment()
    private lateinit var image:String
    private val File = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val pref: String = preferences.getString("UsuarioJson", "")!!
        if(!pref.equals("")){

            var auth = FirebaseAuth.getInstance()

            val user = JSONObject(pref)
            val correoU:String = user.getString("correo")
            val contrasniaU:String = user.getString("pass")

            auth.signInWithEmailAndPassword(correoU, contrasniaU)
                .addOnCompleteListener(this){
                        task->
                    if(task.isSuccessful){
                        Toast.makeText(this, "Se detectó una sesion iniciada", Toast.LENGTH_SHORT).show()
                        action()
                    }
                }
        }

        supportFragmentManager.beginTransaction().replace(R.id.container, login).commit()

    }
    fun onClickAbReg(view: View) { //cambiar a pantalla de registro
        supportFragmentManager.beginTransaction().replace(R.id.container, register).commit()
    }

    fun onClickVolver(view: View) {
        supportFragmentManager.beginTransaction().replace(R.id.container, login).commit()
    }

    fun onClickLogin(view: View) {
        loginUser()
    }

    private fun loginUser(){

        var txtUser: EditText = findViewById(R.id.TB_EmailLogin)
        var txtPassword: EditText = findViewById(R.id.TB_PasswordLogin)
        var auth = FirebaseAuth.getInstance()

        val user:String=txtUser.text.toString()

        val password:String=txtPassword.text.toString()

        if(!TextUtils.isEmpty(user)&&!TextUtils.isEmpty(password)){
            auth.signInWithEmailAndPassword(user,password)
                .addOnCompleteListener(this){
                    task->
                    if(task.isSuccessful){
                        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                        val editor: SharedPreferences.Editor = preferences.edit()

                        val jsonObject= JSONObject()
                        jsonObject.put("correo", user)
                        jsonObject.put("pass", password)

                        editor.putString("UsuarioJson", jsonObject.toString())
                        editor.apply()
                        action()
                    }else{
                        Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            Toast.makeText(this,"Llene todos los campos",Toast.LENGTH_SHORT).show()
        }
    }

    private fun action(){
        startActivity(Intent(this,ActivityMenu::class.java))
    }

    fun onClickAdjuntar(view: View) {
        fileUpload()
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

    fun isPasswordValid(password: String): Boolean {
        val regEx = "^(?=.*\\d)(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z])(?=.*[a-z])\\S{8,16}\$"
        val inputStr: CharSequence = password
        val pattern: Pattern = Pattern.compile(regEx, Pattern.UNICODE_CASE)
        val matcher: Matcher = pattern.matcher(inputStr)
        return matcher.matches()
    }

    fun onClickRegistrar(view: View) {//Registro

        var txtName: EditText =findViewById(R.id.TB_NombreU)
        var txtEmail: EditText =findViewById(R.id.TB_EmailU)
        var txtApellidoP: EditText =findViewById(R.id.TB_ApPatU)
        var txtApellidoM: EditText =findViewById(R.id.TB_ApMatU)
        var txtUsuario: EditText =findViewById(R.id.TB_UsuarioU)
        var txtContrasenia: EditText =findViewById(R.id.TB_ContraseñaU)
        var txtConfirmar: EditText =findViewById(R.id.TB_ConfirmarU)

        var nombreU: String = txtName.text.toString()
        var emailU: String = txtEmail.text.toString()
        var apellidoP: String = txtApellidoP.text.toString()
        var apellidoM: String = txtApellidoM.text.toString()
        var usuarioU: String = txtUsuario.text.toString()
        var contraseñaU: String = txtContrasenia.text.toString()
        var confirmar: String = txtConfirmar.text.toString()

        if (!TextUtils.isEmpty(nombreU) && !TextUtils.isEmpty(emailU) && !TextUtils.isEmpty(apellidoP) && !TextUtils.isEmpty(apellidoM) && !TextUtils.isEmpty(usuarioU) && !TextUtils.isEmpty(contraseñaU)) {//revisamos que no estan vacios
            if(isPasswordValid(contraseñaU))
                if(contraseñaU == confirmar) {

                    var db = UsuarioController()
                    db.createNewAccount(emailU, nombreU, apellidoP, apellidoM, usuarioU, contraseñaU, image)

                    var auth = FirebaseAuth.getInstance()

                    auth.signInWithEmailAndPassword(emailU, contraseñaU)
                        .addOnCompleteListener(this){
                                task->
                            if(task.isSuccessful){
                                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                                action()
                            }
                        }
                }
                else{
                    Toast.makeText(this, "Las contraseñas son distintas", Toast.LENGTH_SHORT).show()
                }
            else{
                Toast.makeText(this, "Contraseña no valida", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
        }

    }

}