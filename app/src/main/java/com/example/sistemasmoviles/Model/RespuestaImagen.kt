package com.example.sistemasmoviles.Model

import java.util.*

data class RespuestaImagen(
    var id:Int,
    var Fk_Publicacion:Int,
    var Url:String,
    var created_at:Date,
    var updated_at:Date
)
