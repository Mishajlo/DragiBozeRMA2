package com.example.dragiboze.models.repository.implemenation

import com.example.dragiboze.database.baza.AppDatabase
import com.example.dragiboze.database.entities.IgraDbModel
import com.example.dragiboze.database.entities.MacaDbModel
import com.example.dragiboze.models.api.interfaces.MacApi
import com.example.dragiboze.views.kviz.KvizContract
import com.example.dragiboze.models.repository.interfaces.KvizRepository
import com.example.meoworld.data.datastore.UserStore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import kotlin.collections.plus
import kotlin.random.Random

class KvizRepositoryImpl @Inject constructor(
    private val baza: AppDatabase,
    private val stor: UserStore,
    private val macApi: MacApi
): KvizRepository {

    private var temperamenti: List<String> = emptyList()

    override suspend fun pitanja(): List<KvizContract.Pitanje> = coroutineScope {
        val pitanjca = mutableListOf<Deferred<KvizContract.Pitanje>>()
        repeat(20) {
            pitanjca += async(Dispatchers.IO) {
                val random = Random(2)
                if (random.nextInt().mod(2) == 0){
                    tipJedan()
                }else{
                    tipDva()
                }
            }
        }
        pitanjca.awaitAll()
    }

    override suspend fun objaviRez(rezultat: Double): Double {
        baza.igraDao().insert(
            IgraDbModel(
                korisnik = stor.getUserData().korisnickoIme,
                rezultat = rezultat,
                vremeKreiranja = System.currentTimeMillis(),
                objavi = false
            )
        )
        return rezultat
    }

    private suspend fun tipJedan(): KvizContract.Pitanje {
        val rasa = raseSaSLikama().random()
        val url = baza.slikaDao().getAllById(rasa.id)

        val sveRase = baza.macaDao().getAll()
        val odgovori = sveRase
            .filter { it.id != rasa.id }
            .shuffled()
            .take(3)
            .map { it.name.lowercase() } + rasa.name.lowercase()

        return KvizContract.Pitanje(
            tekst = "Koja rasa je u pitanju?",
            idMace = rasa.id,
            slikaMace = url.toString(),
            odgovori = odgovori.shuffled(),
            tacanOdgovor = rasa.name.lowercase()
        )
    }

    private suspend fun tipDva(): KvizContract.Pitanje {
        val rasa = raseSaSLikama().random()
        val url = baza.slikaDao().getAllById(rasa.id)

        val temperamentiRase = rasa.temperament.split(",").map { it.trim().lowercase() }
        val sviTemperamenti = temperaments().map { it.trim() }

        val lazniTemperamenti = sviTemperamenti.filter { it !in temperamentiRase }.shuffled()
        val tacanOdgovor = lazniTemperamenti.random()

        val odgovori = temperamentiRase.take(3) + tacanOdgovor

        return KvizContract.Pitanje(
            tekst = "Koji ne pripada?",
            idMace = rasa.id,
            slikaMace = url.toString(),
            odgovori = odgovori.shuffled(),
            tacanOdgovor = tacanOdgovor
        )
    }

    private suspend fun temperaments(): List<String> {
        if (temperamenti.isEmpty()) {
            temperamenti = baza.macaDao().getAllTemperaments()
        }
        return temperamenti
    }

    private suspend fun raseSaSLikama(): List<MacaDbModel> {
        return baza.macaDao().getAll().filter { maca ->
            baza.macaDao().izbrojSlikeZaMacu(maca.id) > 0
        }
    }

}