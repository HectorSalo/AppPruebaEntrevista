package com.thefactoryhka.apppruebaentrevista.ui.baseDeDatos

import androidx.room.*

@Dao
interface EmisorDao {

    @Query("SELECT * FROM emisor_table")
    fun getAll(): List<Emisor>


    @Query("SELECT * FROM emisor_table WHERE id = :id")
    suspend fun getById(id: Int): Emisor

    @Update
    suspend fun update(emisor: Emisor)

    @Insert
    suspend fun insert(emisor: Emisor)

    @Query("DELETE FROM emisor_table")
    suspend fun deleteAll()

}