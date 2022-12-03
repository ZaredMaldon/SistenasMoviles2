package com.example.sistemasmoviles.Model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class FireBase(var tableName:String){
    var database: FirebaseDatabase=FirebaseDatabase.getInstance()
    var dbReference: DatabaseReference=database.reference.child(tableName)

}
