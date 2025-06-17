package com.example.dragiboze.models.repository.implemenation

import com.example.dragiboze.database.baza.AppDatabase
import com.example.dragiboze.database.entities.TakmicarDbModel
import com.example.dragiboze.mappers.asTakmicarDbModel
import com.example.dragiboze.models.api.interfaces.TakmicarApi
import com.example.dragiboze.models.api.models.TakmicarApiModel
import com.example.dragiboze.models.api.models.rezultatReq
import com.example.dragiboze.models.repository.interfaces.TakmicarRepository
import com.example.meoworld.data.datastore.UserStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TakmicarRepositoryApiImpl @Inject constructor(
    private val baza: AppDatabase,
    private val stor: UserStore,
    private val takmicarApi: TakmicarApi
): TakmicarRepository {

    override suspend fun sviTakmicari(idKategorije: Int): List<TakmicarApiModel> {
        var takmicari = emptyList<TakmicarApiModel>()
        withContext(Dispatchers.IO){
            takmicari = takmicarApi.getLeaderboard(idKategorije)
        }
        return takmicari
    }

    override suspend fun objaviRezultat(idKategorije: Int, rezultat: Double) {
        withContext(Dispatchers.IO){
            takmicarApi.postLeaderboard(
                rezultatReq(
                    nickname = stor.getUserData().korisnickoIme,
                    result = rezultat,
                    category = idKategorije
                )
            )
        }


        baza.igraDao().getLast().let {
            it.objavi = true
            baza.igraDao().update(it)
        }

    }

    override fun observeTakmicare(): Flow<List<TakmicarDbModel>> {
        return emptyFlow()
    }

}