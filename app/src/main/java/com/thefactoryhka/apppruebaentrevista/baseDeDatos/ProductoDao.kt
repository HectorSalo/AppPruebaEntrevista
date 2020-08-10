package com.thefactoryhka.apppruebaentrevista.baseDeDatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductoDao {
    @Query("SELECT * FROM producto_table")
    suspend fun getAll(): List<Producto>


    @Query("SELECT * FROM producto_table WHERE id = :id")
    suspend fun getById(id: Int): Producto

    @Update
    suspend fun update(producto: Producto)

    @Insert
    suspend fun insert(producto: Producto)

    @Query("DELETE FROM producto_table")
    suspend fun deleteAll()
}