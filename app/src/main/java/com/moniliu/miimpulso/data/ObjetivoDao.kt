package com.moniliu.miimpulso.data

import android.content.ContentValues
import android.content.Context
import com.moniliu.miimpulso.utils.DataBaseManager

class ObjetivoDao ( val context: Context){
    fun insert(objetivo: Objetivo){

        val db = DataBaseManager(context).writableDatabase
        val values = ContentValues()

        values.put("titulo", objetivo.titulo)
        values.put("categoria", objetivo.categoria)
        values.put("completado", if (objetivo.completado) 1 else 0)

        db.insertOrThrow("objetivos", null,  values)
        db.close()
    }
}
