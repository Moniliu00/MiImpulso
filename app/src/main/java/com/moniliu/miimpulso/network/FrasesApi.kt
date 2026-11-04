package com.moniliu.miimpulso.network

import retrofit2.Call
import retrofit2.http.GET

interface FraseApi {
    @GET("phrase/esp")
    fun obtenerFrase(): Call<Frase>

}