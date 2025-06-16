package com.example.dragiboze.models.repository.interfaces

import com.example.dragiboze.database.entities.TakmicarDbModel
import kotlinx.coroutines.flow.Flow

interface TakmicarRepository {

    suspend fun sviTakmicari(idKategorije: Int = 1)

    suspend fun objaviRezultat(idKategorije: Int = 1, rezultat: Double)

    fun observeTakmicare(): Flow<List<TakmicarDbModel>>

}