package com.example.sistemasmoviles.DB

data class API(var id:String?,var usuario:String?){
    var url= "http://127.0.0.1:8000/api"

    fun urlId():String{
        var newUrl=url+"/publicaciones/"+id;
        return newUrl
    }

    fun urlUser():String{
        var newUrl=url+"/publicaciones/2/"+usuario
        return newUrl
    }

}
