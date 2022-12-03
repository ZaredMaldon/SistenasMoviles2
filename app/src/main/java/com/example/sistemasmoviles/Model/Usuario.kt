package com.example.sistemasmoviles.Model

class Usuario {
    var email: String? = null;
    var nombre: String? = null;
    var apPat: String? = null;
    var apMat: String? = null;
    var usuario: String? = null;
    var contraseña: String? = null;
    var status: Boolean? = null;

    constructor() {}
    constructor(texto: String?) {
        nombre = texto
    }
}
