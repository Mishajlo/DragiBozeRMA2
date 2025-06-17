package com.example.dragiboze.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dragiboze.database.entities.SlikaDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SlikaDao {

    @Query("Select * from slike where idRase = :idMace")
    suspend fun getAllById(idMace: String): List<SlikaDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insert(slikaDbModel: SlikaDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(listSlika: List<SlikaDbModel>)

    @Query("Select count(*) from slike where idRase = :idMace")
    suspend fun countImagesById(idMace: String): Int

    @Query("Select * from slike where idRase = :idMace")
    fun observeAllById(idMace: String): Flow<List<SlikaDbModel>>


}