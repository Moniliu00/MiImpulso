package com.moniliu.miimpulso.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
// gestiona la creación de la base de datos
class DataBaseManager (context: Context) :
        SQLiteOpenHelper(context, "miimpulso.db", null,3){

    // crea la tabla cuando se utiliza la base de datos por primera vez

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            //define donde se guarda mis objetivos
           """
               CREATE TABLE objetivos (
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               titulo TEXT NOT NULL,
               categoria TEXT NOT NULL,
               completado INTEGER NOT NULL DEFAULT 0,
               description TEXT NOT NULL DEFAULT '')
               """.trimIndent()

        )
    }
    // servira para actualizar la estructura en futuras versiones

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        val cursor = db.rawQuery(
            "PRAGMA table_info(objetivos)",
            null
        )
        var existeDescripcion = false

        cursor.use {

            while (it.moveToNext()){
                val nombre = it.getString(
                    it.getColumnIndexOrThrow("name")
                )
                if (nombre == "descripcion"){
                    existeDescripcion = true
                }
            }
        }
       if (!existeDescripcion){
          db.execSQL (
              "ALTER TABLE objetivos ADD COLUMN description TEXT NOT NULL DEFAULT ''"
          )
       }
    }
}