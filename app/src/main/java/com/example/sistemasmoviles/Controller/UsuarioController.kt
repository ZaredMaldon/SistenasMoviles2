package com.example.sistemasmoviles.Controller

import com.example.sistemasmoviles.Model.Usuario

class UsuarioController(var model:Usuario) {
    fun getUserName():String{
        return model.name
    }
    fun getUserid(): Int? {
        return model.id
    }
    fun setUser(name:String,apPat:String,apMat:String,email:String,user:String, password:String, status:Boolean,  idImage:Int, image:Byte){
        model.name=name
        model.apPat=apPat
        model.apMat=apMat
        model.email=email
        model.user=user
        model.password=password
        model.status=status
        model.idImage=idImage
        model.image=image
    }
    fun getUser():Usuario{
        return model
    }
    fun updateView(){
        /*Se tendria que mandar la view desde los parametros como hicimos en el usuario para hacer esto
        * view.printDetails(model.Name,model.id)
        * siendo view la variable o el nombre que le dimos a esta*/

    }
}