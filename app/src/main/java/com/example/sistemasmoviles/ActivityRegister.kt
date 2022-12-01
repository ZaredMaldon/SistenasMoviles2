package com.example.sistemasmoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.sistemasmoviles.Model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActivityRegister : AppCompatActivity() {
    private lateinit var txtName:EditText
    private lateinit var txtEmail:EditText
    private lateinit var txtUsuario:EditText
    private lateinit var txtPassword1:EditText
    private lateinit var txtPassword2:EditText
    private lateinit var dbReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private lateinit var auth:FirebaseAuth
    private lateinit var  progressBar:ProgressBar



        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //LLAMAMOS A LAS VISTAS
        txtName=findViewById(R.id.TB_Name)
        txtEmail=findViewById(R.id.TB_Email)
        txtUsuario=findViewById(R.id.TB_Usuario)
        txtPassword1=findViewById(R.id.TB_Password)
        txtPassword2=findViewById(R.id.TB_Password2)

        progressBar=findViewById(R.id.progressBar)

        database=FirebaseDatabase.getInstance()
        auth=FirebaseAuth.getInstance()

        dbReference=database.reference.child("User")
    }

    fun onClick(view: View) {//Registro
        createNewAccount()
    }

    private fun createNewAccount(){
        val name:String=txtName.text.toString()
        val email:String=txtEmail.text.toString()
        val usuario:String=txtUsuario.text.toString()
        val password:String=txtPassword1.text.toString()
        val password2:String=txtPassword2.text.toString()

        if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(usuario)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(password2)){//revisamos que no estan vacios
            progressBar.visibility=View.VISIBLE
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                    task->
                    if(task.isComplete){
                        val user: FirebaseUser? =auth.currentUser
                        verifyEmail(user)
                        val userBD=dbReference.child(user?.uid.toString())
                        userBD.child("Name").setValue(name)
                        userBD.child("Usuario").setValue(usuario)
                        userBD.child("Email").setValue(email)
                        userBD.child("Password").setValue(password)
                        action()
                    }
                }
        }
    }

    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                    task->
                if(task.isComplete){
                    Toast.makeText(this,"Email enviado",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Error al enviar el email",Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun action(){
        startActivity(Intent(this,ActivityLogin::class.java))
    }
}