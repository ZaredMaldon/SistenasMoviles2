package com.example.sistemasmoviles.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.sistemasmoviles.Model.Usuario

class DataDbHelper(var context:Context):SQLiteOpenHelper(context,SetDB.DB_NAME,null,SetDB.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        try{
            val createUserTable:String = "CREATE TABLE " + SetDB.tblUsuarios.TABLE_NAME + "(" +
                    SetDB.tblUsuarios.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SetDB.tblUsuarios.COL_NOMBRES + " VARCHAR(50)," +
                    SetDB.tblUsuarios.COL_APPATERNO + " VARCHAR(25)," +
                    SetDB.tblUsuarios.COL_APMATERNO + " VARCHAR(25)," +
                    SetDB.tblUsuarios.COL_CORREO + " VARCHAR(60)," +
                    SetDB.tblUsuarios.COL_USUARIO + " VARCHAR(50)," +
                    SetDB.tblUsuarios.COL_PASSWORD + " VARCHAR(25)," +
                    SetDB.tblUsuarios.COL_ESTATUS + " BOOL," +
                    SetDB.tblUsuarios.COL_IDIMAGE + " INTEGER," +
                    SetDB.tblUsuarios.COL_IMG + " BLOB)"
            db?.execSQL(createUserTable)

            val createPetTable:String = "CREATE TABLE " + SetDB.tblMascotas.TABLE_NAME + "(" +
                    SetDB.tblMascotas.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SetDB.tblMascotas.COL_NOMBRE + " VARCHAR(50)," +
                    SetDB.tblMascotas.COL_FKTIPOANIMAL + " INTEGER," +
                    SetDB.tblMascotas.COL_IDIMAGE + " INTEGER," +
                    SetDB.tblMascotas.COL_IMAGEN + " BLOB)"
            db?.execSQL(createPetTable)

            val createPostTable:String = "CREATE TABLE " + SetDB.tblPublicaciones.TABLE_NAME + "(" +
                    SetDB.tblPublicaciones.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SetDB.tblPublicaciones.COL_TITLE + " VARCHAR(25)," +
                    SetDB.tblPublicaciones.COL_DESCRIPCION + " VARCHAR(256)," +
                    SetDB.tblPublicaciones.COL_FKUSUARIO + " INTEGER," +
                    SetDB.tblPublicaciones.COL_FKMASCOTA + " INTEGER," +
                    SetDB.tblPublicaciones.COL_ESTATUS + " BOOL," +
                    SetDB.tblPublicaciones.COL_IMG + " BLOB," +
                    SetDB.tblPublicaciones.COL_FECHAPUBLICACION + " DATE)"
            db?.execSQL(createPostTable)

            val createTypesTable:String = "CREATE TABLE " + SetDB.tblTipos.TABLE_NAME + "(" +
                    SetDB.tblTipos.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SetDB.tblTipos.COL_TIPO + " VARCHAR(30))"
            db?.execSQL(createTypesTable)

            Log.e("ENTRO","CREO TABLAS")

        }catch (e:Exception){
            Log.e("Execption", e.toString())
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    /*-----------------------------------Usuarios-------------------------------------------------*/
    public fun insertUser(usuario: Usuario):Boolean{

        val dataBase:SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult:Boolean =  true

        values.put(SetDB.tblUsuarios.COL_ID,usuario.id)
        values.put(SetDB.tblUsuarios.COL_NOMBRES,usuario.name)
        values.put(SetDB.tblUsuarios.COL_APPATERNO,usuario.apPat)
        values.put(SetDB.tblUsuarios.COL_APMATERNO,usuario.apMat)
        values.put(SetDB.tblUsuarios.COL_CORREO,usuario.email)
        values.put(SetDB.tblUsuarios.COL_USUARIO,usuario.user)
        values.put(SetDB.tblUsuarios.COL_PASSWORD,usuario.password)
        values.put(SetDB.tblUsuarios.COL_ESTATUS,usuario.status)
        values.put(SetDB.tblUsuarios.COL_IDIMAGE,usuario.idImage)
        values.put(SetDB.tblUsuarios.COL_IMG,usuario.image)

        try {
            val result =  dataBase.insert(SetDB.tblUsuarios.TABLE_NAME, null, values)

            if (result == (0).toLong()) {
                Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
            }

        }catch (e: Exception){
            Log.e("Execption", e.toString())
            boolResult =  false
        }

        dataBase.close()

        return boolResult
    }
    /*-----------------------------------Mascotas-------------------------------------------------*/

    /* --------------------------------Publicaciones----------------------------------------------*/


}