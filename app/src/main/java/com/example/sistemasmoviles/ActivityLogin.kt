package com.example.sistemasmoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


class ActivityLogin : AppCompatActivity() {
    private lateinit var txtUser:EditText
    private lateinit var txtPassword:EditText
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUser=findViewById(R.id.TB_EmailLogin)
        txtPassword=findViewById(R.id.TB_PasswordLogin)
        auth=FirebaseAuth.getInstance()


    }

    fun onClickAbReg(view: View) { //cambiar a pantalla de registro
        val change = Intent(this,ActivityRegister::class.java)
        startActivity(change)
    }

    fun onClickLogin(view: View) {
        loginUser()
    }

    private fun loginUser(){
        val user:String=txtUser.text.toString()
        val password:String=txtPassword.text.toString()

        if(!TextUtils.isEmpty(user)&&!TextUtils.isEmpty(password)){
            auth.signInWithEmailAndPassword(user,password)
                .addOnCompleteListener(this){
                    task->
                    if(task.isSuccessful){
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
        startActivity(Intent(this,MainActivity::class.java))
    }
}