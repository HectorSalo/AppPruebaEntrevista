package com.thefactoryhka.apppruebaentrevista.baseDeDatos

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "producto_table")
data class Producto (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val descripcion: String,
    val codigo: Int,
    val precio: Double,
    val cantidad: Int
)