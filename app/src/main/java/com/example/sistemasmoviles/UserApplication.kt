package com.example.sistemasmoviles

import android.app.Application
import com.example.sistemasmoviles.DB.DataDbHelper

class UserApplication:Application() {
    companion object{
        lateinit var dbHelper: DataDbHelper
    }

    override fun onCreate() {
        super.onCreate()
        dbHelper =  DataDbHelper(applicationContext)
    }
}