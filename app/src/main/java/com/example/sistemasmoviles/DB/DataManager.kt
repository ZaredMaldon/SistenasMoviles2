package com.example.sistemasmoviles.DB

import com.example.sistemasmoviles.Model.Tipo

object DataManager {
    val tipos = ArrayList<Tipo>()

    init{
        this.initializeTipos()
    }

    private fun initializeTipos(){
        var Tipo = Tipo(1,"Perro")
        tipos.add(Tipo)
        Tipo = Tipo(2,"Gato")
        tipos.add(Tipo)
        Tipo = Tipo(3,"Serpiente")
        tipos.add(Tipo)
        Tipo = Tipo(4,"Hamster")
        tipos.add(Tipo)
        Tipo = Tipo(5,"Rata")
        tipos.add(Tipo)
        Tipo = Tipo(6,"Pez")
        tipos.add(Tipo)
        Tipo = Tipo(7,"rana")
        tipos.add(Tipo)
        Tipo = Tipo(8,"Tortuga")
        tipos.add(Tipo)
        Tipo = Tipo(9,"Pajaro")
        tipos.add(Tipo)
        Tipo = Tipo(10,"Conejo")
        tipos.add(Tipo)
        Tipo = Tipo(11,"Iguana")
        tipos.add(Tipo)
        Tipo = Tipo(12,"Otros")
        tipos.add(Tipo)
    }
}