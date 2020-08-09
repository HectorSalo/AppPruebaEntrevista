package com.thefactoryhka.apppruebaentrevista.ui.baseDeDatos

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Cliente::class, Emisor::class], version = 2)
abstract class ReciboDB: RoomDatabase() {

    abstract fun clienteDao(): ClienteDao

    abstract fun emisorDao(): EmisorDao

}



