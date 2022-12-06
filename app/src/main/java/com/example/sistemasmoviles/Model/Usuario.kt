package com.example.sistemasmoviles.Model

class Usuario {
    var email: String? = null
    var nombre: String? = null
    var apPat: String? = null
    var apMat: String? = null
    var usuario: String? = null
    var contraseña: String? = null

    constructor()
    constructor(emailU: String?, nombreU: String?, apPatU: String?, apMatU: String?, usuarioU: String?, contraseñaU: String?) {
        email = emailU
        nombre = nombreU
        apPat = apPatU
        apMat = apMatU
        usuario = usuarioU
        contraseña = contraseñaU
    }
}
