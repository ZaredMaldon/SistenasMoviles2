package com.example.sistemasmoviles.DB

class SetDB {
    //DECLARAMOS  EL NOMBRE Y VERSION DE TAL FOR QUE PUEDA SER VISIBLES PARA CUALQUIER CLASE
    companion object{
        val DB_NAME =  "bdProjectPetme"
        val DB_VERSION =  1
    }

    //VAMOS ES DEFINIR EL ESQUEMA DE UNA DE LAS TABLAS
    abstract class tblUsuarios{
        //DEFINIMOS LOS ATRIBUTOS DE LA CLASE USANDO CONTANTES
        companion object{
            val TABLE_NAME = "Usuarios"
            val COL_ID =  "intID"
            val COL_NOMBRES =  "strNombres"
            val COL_APPATERNO = "strApPaterno"
            val COL_APMATERNO = "strApMaterno"
            val COL_CORREO= "strCorreo"
            val COL_USUARIO= "strUsuario"
            val COL_PASSWORD= "strPassword"
            val COL_ESTATUS= "boolEstatus"
            val COL_IDIMAGE =  "intIdImage"
            val COL_IMG =  "imgArray" // byte Array image
        }
    }

    abstract class tblMascotas{
        companion object{
            val TABLE_NAME = "Mascotas"
            val COL_ID =  "intID"
            val COL_NOMBRE = "strNombre"
            val COL_DESCRIPCION = "strDescripcion"
            val COL_FKTIPOANIMAL = "intFkTipoAnimal"
            val COL_IDIMAGE = "intIdImage"
            val COL_IMAGEN = "imgArray"
        }
    }

    abstract class tblPublicaciones{
        companion object{
            val TABLE_NAME = "Publicaciones"
            val COL_ID =  "intID"
            val COL_TITLE =  "strTitle"
            val COL_PUBLICACION = "strPublicacion"
            val COL_FKUSUARIO = "intFkUsuario"
            val COL_FKMASCOTA = "intFkMascota"
            val COL_ESTATUS = "boolEstatus"
            val COL_IMG =  "imgArray" // byte Array image
            val COL_FECHAPUBLICACION = "dateFechaPublicacion"
        }
    }

    abstract class tblTipos{
        companion object{
            val TABLE_NAME = "Tipos"
            val COL_ID = "intID"
            val COL_TIPO = "strTipo"
        }
    }

}