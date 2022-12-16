package com.example.sistemasmoviles.Model

import com.google.gson.annotations.SerializedName
import java.sql.Blob

data class Publicacion(
    var Nombre:String,
    var Edad:String,
    var Tipo:String,
    var Descripcion:String,
    var MeGusta:Int,
    var Estatus:Int,
    var Usuario:String
)
