package com.thefactoryhka.apppruebaentrevista.baseDeDatos

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Cliente::class, Emisor::class, Producto::class, Recibo::class], version = 4)
abstract class ReciboDB: RoomDatabase() {

    abstract fun clienteDao(): ClienteDao

    abstract fun emisorDao(): EmisorDao

    abstract fun productoDao(): ProductoDao

    abstract fun reciboDao(): ReciboDao

}



