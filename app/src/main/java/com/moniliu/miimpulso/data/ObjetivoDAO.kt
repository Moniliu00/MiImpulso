package com.moniliu.miimpulso.data

import android.content.ContentValues
import android.content.Context
import com.moniliu.miimpulso.utils.DataBaseManager

class ObjetivoDAO (val context: Context){
    fun insert(objetivo: Objetivo){

        val db = DataBaseManager(context).writableDatabase
        val values = ContentValues()

        values.put("titulo", objetivo.titulo)
        values.put("categoria", objetivo.categoria)
        values.put("completado", if (objetivo.completado) 1 else 0)
        values.put("description", objetivo.descripcion)

        db.insertOrThrow("objetivos", null,  values)
        db.close()
    }

    // crea una funcion para consultar los objetivos

    fun findAll(): List<Objetivo>{

        // mutable list prepara la lista donde añadir los resultados

        val listaObjetivos = mutableListOf<Objetivo>()

        // abre la base de datos para leer

        val db = DataBaseManager(context).readableDatabase

        // pide todos los registros de la tabla

        val cursor =db.rawQuery("SELECT * FROM objetivos",null)



        // recorre los registros uno por uno

        cursor.use {
            while (it.moveToNext()){

                val id = it.getInt(it.getColumnIndexOrThrow("id"))

                val titulo = it.getString(it.getColumnIndexOrThrow("titulo"))

                val categoria = it.getString(it.getColumnIndexOrThrow("categoria"))

                val completado = it.getInt(it.getColumnIndexOrThrow("completado")) == 1

                val descripcion= it.getString(
                    it.getColumnIndexOrThrow("description")
                )

                val objetivo = Objetivo(
                    id,
                    titulo,
                    categoria,
                    completado,
                    descripcion)


                listaObjetivos.add(objetivo)
            }
        }

       db.close()
        return listaObjetivos

    }
    fun update (objetivo: Objetivo){

        val db = DataBaseManager(context).writableDatabase

        // prepara los datos que vamos a modificar

        val values = ContentValues()

        values.put ( "completado", if (objetivo.completado) 1 else 0)

        //actualiza el objetivo que tenga el ID indicado
        db.update("objetivos", values, "id = ?", arrayOf(objetivo.id.toString()))

        // cierra la coneccion
        db.close()

    }

    // crea una funcion para eliminar
    fun delete(objetivo: Objetivo){

        // abre la base de datos para modificarla
        val db = DataBaseManager(context).writableDatabase

        // elimina un registro de SQLite

        db.delete(
            "objetivos",
            "id = ?",
            arrayOf(objetivo.id.toString())
        )
        db.close()

    }
}
