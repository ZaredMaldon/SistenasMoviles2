package com.example.sistemasmoviles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class FragmentEditar : Fragment() {

    private lateinit var txtName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtApellidoP: EditText
    private lateinit var txtApellidoM: EditText
    private lateinit var txtUsuario: EditText
    private lateinit var txtContraseña: EditText
    private lateinit var txtConfirmar: EditText

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userFirebase: FirebaseUser

    private lateinit var mDataBase: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtName = view.findViewById(R.id.TB_NombreU)
        txtEmail= view.findViewById(R.id.TB_EmailU)
        txtApellidoP= view.findViewById(R.id.TB_ApPatU)
        txtApellidoM= view.findViewById(R.id.TB_ApMatU)
        txtUsuario= view.findViewById(R.id.TB_UsuarioU)
        txtContraseña= view.findViewById(R.id.TB_ContraseñaU)
        txtConfirmar= view.findViewById(R.id.TB_ConfirmarU)

        firebaseAuth = FirebaseAuth.getInstance()
        userFirebase = firebaseAuth.currentUser!!

        mDataBase = FirebaseDatabase.getInstance().getReference("User")

        mDataBase.child(userFirebase.uid).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    var nombre = snapshot.child("Name").value.toString()
                    var apellidoP = snapshot.child("ApellidoPaterno").value.toString()
                    var apellidoM = snapshot.child("ApellidoMaterno").value.toString()
                    var email = snapshot.child("Email").value.toString()
                    var usuario = snapshot.child("Usuario").value.toString()
                    var contraseña = snapshot.child("Password").value.toString()

                    txtName.setText(nombre)
                    txtEmail.setText(email)
                    txtApellidoP.setText(apellidoP)
                    txtApellidoM.setText(apellidoM)
                    txtUsuario.setText(usuario)
                    txtContraseña.setText(contraseña)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

}