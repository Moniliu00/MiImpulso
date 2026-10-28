package com.moniliu.miimpulso.data

data class Objetivo (
    val id: Int = -1,
    var titulo: String,
    var categoria: String,
    var completado: Boolean = false
)