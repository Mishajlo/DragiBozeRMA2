package com.example.dragiboze.models.repository.interfaces

import com.example.dragiboze.database.entities.MacaDbModel
import com.example.dragiboze.database.entities.SlikaDbModel
import com.example.dragiboze.models.api.models.MacApiModel
import com.example.dragiboze.models.api.models.MackicaSlicica
import kotlinx.coroutines.flow.Flow

interface MjauRepository {

    suspend fun getAllMace(): List<MacaDbModel>

    fun observeMace(): Flow<List<MacaDbModel>>

    fun obsereveMaca(macaId: String): Flow<MacaDbModel?>

    suspend fun getImagesForMaca(macaId: String): List<SlikaDbModel>

    fun observeImagesForMaca(macaId: String): Flow<List<SlikaDbModel>>

}