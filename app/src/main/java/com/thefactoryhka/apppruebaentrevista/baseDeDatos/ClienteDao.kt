package com.thefactoryhka.apppruebaentrevista.baseDeDatos

import androidx.room.*

@Dao
interface ClienteDao {

    @Query("SELECT * FROM cliente_table")
    suspend fun getAll(): List<Cliente>


    @Query("SELECT * FROM cliente_table WHERE id = :id")
    suspend fun getById(id: Int): Cliente

    @Update
    suspend fun update(cliente: Cliente)

    @Insert
    suspend fun insert(cliente: Cliente)

    @Query("DELETE FROM cliente_table")
    suspend fun deleteAll()

}