package com.moniliu.miimpulso.utils

import android.content.Context

// permite acceder a los recursos de la aplicacion

class SessionManager (context: Context) {

    // abre un pequeño almacenamiento de datos
    private  val preferences = context.getSharedPreferences(
        "MiImpulsoPrefs",
        Context.MODE_PRIVATE
    )

    // gurada el nombre del usuario
    fun guardarNombre(nombre: String){
        preferences.edit()
            .putString("nombre", nombre)

            // aplica el cambio para que quede guardado
            .apply()


    }
    fun obtenerNombre(): String{
        return  preferences.getString("nombre", "") ?: ""
    }
}