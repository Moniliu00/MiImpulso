package com.moniliu.miimpulso.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseManager (context: Context) :
        SQLiteOpenHelper(context, "miimpulso.db", null,1){

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
           """
               CREATE TABLE objetivos (
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               titulo TEXT NOT NULL,
               categoria TEXT NOT NULL,
               completado INTEGER NOT NULL DEFAULT 0)
               """.trimIndent()

        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // por ahora no necesitamos migraciones
    }
}