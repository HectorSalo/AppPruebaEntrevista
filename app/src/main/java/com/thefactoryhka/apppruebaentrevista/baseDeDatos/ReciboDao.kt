package com.thefactoryhka.apppruebaentrevista.baseDeDatos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReciboDao {
    @Query("SELECT * FROM recibo_table")
    suspend fun getAll(): List<Recibo>


    @Query("SELECT * FROM recibo_table WHERE id = :id")
    suspend fun getById(id: Int): Recibo

    @Update
    suspend fun update(recibo: Recibo)

    @Insert
    suspend fun insert(recibo: Recibo)

    @Query("DELETE FROM recibo_table")
    suspend fun deleteAll()
}