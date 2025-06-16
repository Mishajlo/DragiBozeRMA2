package com.example.dragiboze.models.repository.implemenation

import com.example.dragiboze.database.baza.AppDatabase
import com.example.dragiboze.database.entities.IgraDbModel
import com.example.dragiboze.datastore.UserData
import com.example.dragiboze.models.repository.interfaces.KorisnikRepository
import com.example.dragiboze.models.repository.interfaces.TakmicarRepository
import com.example.meoworld.data.datastore.UserStore
import javax.inject.Inject

class KorisnikRepositoryImpl @Inject constructor(
    private val baza: AppDatabase,
    private val stor: UserStore,
    private val takmicarRepo: TakmicarRepository
    ): KorisnikRepository {

    override suspend fun getUserData(): UserData {
        return stor.getUserData()
    }

    override suspend fun register(userData: UserData): String {
        stor.setData(userData)
        return stor.getUserData().korisnickoIme
    }

    override suspend fun getAllIgre(): List<IgraDbModel> {
        return baza.igraDao().getAll()
    }

    override suspend fun getUserIgre(): List<IgraDbModel> {
        return baza.igraDao().getIgreByKorisnik(stor.getUserData().korisnickoIme)
    }

    override suspend fun getNo1(): Pair<Int, Double> {
        if (baza.igraDao().getAll().isEmpty()){
            takmicarRepo.sviTakmicari()
        }
        return baza.takmicarDao().getBestForKorisnik(stor.getUserData().korisnickoIme)
    }
}