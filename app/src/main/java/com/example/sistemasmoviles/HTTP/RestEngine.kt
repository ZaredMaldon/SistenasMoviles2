package com.example.sistemasmoviles.HTTP

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestEngine {
    // nos permite acceder sin instanciar el objecto
    companion object{
        fun getRestEngine(): Retrofit {
            //Creamos el interceptor
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client =  OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit =  Retrofit.Builder()
                .baseUrl("https://apipublicaciones.zambiaa.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return  retrofit

        }
    }
}