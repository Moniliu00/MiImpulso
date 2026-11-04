package com.moniliu.miimpulso.network
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://positive-api.online/")
        .addConverterFactory (GsonConverterFactory.create())
        .build()

    val api : FraseApi = retrofit.create(FraseApi::class.java)

}