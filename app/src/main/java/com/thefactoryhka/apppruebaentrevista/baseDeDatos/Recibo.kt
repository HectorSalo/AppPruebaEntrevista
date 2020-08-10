package com.thefactoryhka.apppruebaentrevista.baseDeDatos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recibo_table")
data class Recibo (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val cliente: String,
    val emisor: String,
    val total: Double,
    val cantidadItems: Int
)