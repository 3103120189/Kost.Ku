package com.example.kostku.room

import androidx.room.*

@Dao
interface KostDao {

    @Insert
    suspend fun addKost(kost: Kost)

    @Update
    suspend fun updateKost(kost: Kost)

    @Delete
    suspend fun deleteKost(kost: Kost)

    @Query("SELECT * FROM kost")
    suspend fun getkostn():List<Kost>

    @Query("SELECT * FROM kost WHERE id=:kost_id")
    suspend fun getkost(kost_id: Int): List<Kost>

}