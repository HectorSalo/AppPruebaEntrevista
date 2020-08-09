package com.thefactoryhka.apppruebaentrevista.ui.baseDeDatos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emisor_table")
data class Emisor (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val razonSocial: String,
    val rif: Int,
    val identificador: Int
)