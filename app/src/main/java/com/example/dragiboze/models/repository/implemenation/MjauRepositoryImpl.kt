package com.example.dragiboze.models.repository.implemenation

import com.example.dragiboze.database.baza.AppDatabase
import com.example.dragiboze.database.entities.MacaDbModel
import com.example.dragiboze.database.entities.SlikaDbModel
import com.example.dragiboze.mappers.asMacaDbModel
import com.example.dragiboze.mappers.asSlikaDbModel
import com.example.dragiboze.models.api.interfaces.MacApi
import com.example.dragiboze.models.repository.interfaces.MjauRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MjauRepositoryImpl @Inject constructor(
    private val baza: AppDatabase,
    private val macApi: MacApi
): MjauRepository {
    override suspend fun getAllMace(): List<MacaDbModel> {
        var mace = baza.macaDao().getAll()
        if(mace.isEmpty()){
            val mackice = macApi.getAllMace()
            mace = mackice.map { it.asMacaDbModel() }
            baza.macaDao().insertAll(mace)

            mace.forEach { breed ->
                val imageCount = baza.slikaDao().countImagesById(breed.id)
                if (imageCount == 0) {
                    val images = macApi.getSlikeZaMacu(breed.id)
                    baza.slikaDao().instertAll(images.map { it.asSlikaDbModel(breed.id) })
                }
            }
        }
        return mace
    }

    override fun observeMace(): Flow<List<MacaDbModel>> {
        return baza.macaDao().observeAll()
    }

    override fun obsereveMaca(macaId: String): Flow<MacaDbModel?> {
        return baza.macaDao().observeMacaById(macaId)
    }

    override suspend fun getImagesForMaca(macaId: String): List<SlikaDbModel> {
        return baza.slikaDao().getAllById(macaId)
    }

    override fun observeImagesForMaca(macaId: String): Flow<List<SlikaDbModel>> {
        return baza.slikaDao().observeAllById(macaId)
    }
}