package com.example.dragiboze.models.repository.implemenation

import com.example.dragiboze.database.baza.AppDatabase
import com.example.dragiboze.database.entities.IgraDbModel
import com.example.dragiboze.database.entities.MacaDbModel
import com.example.dragiboze.models.api.interfaces.MacApi
import com.example.dragiboze.models.data.Kviz
import com.example.dragiboze.models.repository.interfaces.KvizRepository
import com.example.meoworld.data.datastore.UserStore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.plus
import javax.inject.Inject
import kotlin.collections.plus
import kotlin.random.Random

class KvizRepositoryImpl @Inject constructor(
    private val baza: AppDatabase,
    private val stor: UserStore,
    private val macApi: MacApi
): KvizRepository {

    private var temperamenti: List<String> = emptyList()

    override suspend fun pitanja(): List<Kviz.Pitanje> = coroutineScope {
        val questions = mutableListOf<Deferred<Kviz.Pitanje>>()
        val slike = mutableSetOf<String>()
        repeat(20) {
            questions += async(Dispatchers.IO) {
                when (Random.nextInt(2)) {
                    0 -> tipJedan(slike)
                    else -> tipDva(slike)
                }
            }
        }
        questions.awaitAll()
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

    private suspend fun tipJedan(slike: MutableSet<String>): Kviz.Pitanje {
        val rasa = raseSaSLikama().random()
        val url = slikeZaRasu(rasa.id, slike)

        val sveRase = baza.macaDao().getAll()
        val odgovori = sveRase
            .filter { it.id != rasa.id }
            .shuffled()
            .take(3)
            .map { it.name.lowercase() } + rasa.name.lowercase()

        return Kviz.Pitanje(
            tekst = "Which breed is this?",
            idMace = rasa.id,
            slikaMace = url,
            odgovori = odgovori.shuffled(),
            tacanOdgovor = rasa.name.lowercase()
        )
    }

    private suspend fun tipDva(slike: MutableSet<String>): Kviz.Pitanje {
        val rasa = raseSaSLikama().random()
        val url = slikeZaRasu(rasa.id, slike)

        val temperamentiRase = rasa.temperament.split(",").map { it.trim().lowercase() }
        val sviTemperamenti = temperaments().map { it.trim().lowercase() }

        val lazniTemperamenti = sviTemperamenti.filter { it !in temperamentiRase }.shuffled()
        val tacanOdgovor = lazniTemperamenti.random()

        val answers = temperamentiRase.take(3) + tacanOdgovor

        return Kviz.Pitanje(
            tekst = "Which temperament does not belong to this breed?",
            idMace = rasa.id,
            slikaMace = url,
            odgovori = answers.shuffled(),
            tacanOdgovor = tacanOdgovor
        )
    }

    private suspend fun slikeZaRasu(
        idRase: String,
        slike: MutableSet<String>
    ): String {
        val slicice = baza.slikaDao().getAllById(idRase)
        val dobreSlike = slicice.filter { it.url !in slike }
        val izabrane = dobreSlike.randomOrNull() ?: slicice.random() // fallback to duplicate if all used
        slike += izabrane.url
        return izabrane.url
    }


    private suspend fun temperaments(): List<String> {
        if (temperamenti.isEmpty()) {
            temperamenti = baza.macaDao().getAllTemperaments()
        }
        return temperamenti
    }

    private suspend fun raseSaSLikama(): List<MacaDbModel> {
        return baza.macaDao().getAll().filter { breed ->
            baza.macaDao().izbrojSlikeZaMacu(breed.id) > 0
        }
    }

}