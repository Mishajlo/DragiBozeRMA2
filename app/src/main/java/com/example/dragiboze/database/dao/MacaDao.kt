package com.example.dragiboze.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dragiboze.database.entities.MacaDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MacaDao {

    @Query("Select * from mace")
    suspend fun getAll(): List<MacaDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(macaDbModel: MacaDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(macaList: List<MacaDbModel>)

    suspend fun getAllTemperaments(): List<String> {
        return getAll()
            .asSequence()
            .map { it.temperament.split(",") }
            .flatten()
            .toList()
            .map { it.trim() }
            .map { it.lowercase() }
            .distinct()
    }

    @Query("Select * from mace")
    fun observeAll(): Flow<List<MacaDbModel>>

    @Query("Select * from mace where id = :macaId")
    fun observeMacaById(macaId: String): Flow<MacaDbModel?>

    @Query("Select count(*) from slike where idRase = :macaId")
    suspend fun izbrojSlikeZaMacu(macaId: String): Int

}