package com.thefactoryhka.apppruebaentrevista.baseDeDatos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "cliente_table")
data class Cliente (
    @PrimaryKey (autoGenerate = true) val id: Int,
    val nombre: String,
    val apellido: String,
    val cedula: Int
    )