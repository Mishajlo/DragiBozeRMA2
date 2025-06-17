package com.example.dragiboze.models.repository.implemenation

import android.util.Log
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
            Log.d("Potvrda", "Usao")
            val mackice = macApi.getAllMace()
            mace = mackice.map { it.asMacaDbModel() }
            baza.macaDao().insertAll(mace)

            mace.forEach { breed ->
                val imageCount = baza.slikaDao().countImagesById(breed.id)
                if (imageCount == 0) {
                    val images = macApi.getSlikeZaMacu(breed.id)
                    baza.slikaDao().insertAll(images.map { it.asSlikaDbModel(breed.id) })
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
        val images = macApi.getSlikeZaMacu(macaId)
        baza.slikaDao().insertAll(images.map { it.asSlikaDbModel(macaId) })
        Log.d("Imali", baza.slikaDao().getAllById(macaId).isEmpty().toString())
        return baza.slikaDao().getAllById(macaId)
    }

    override fun observeImagesForMaca(macaId: String): Flow<List<SlikaDbModel>> {
        return baza.slikaDao().observeAllById(macaId)
    }
}