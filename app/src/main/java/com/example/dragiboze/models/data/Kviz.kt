package com.example.dragiboze.models.data

interface Kviz {

    data class Pitanje(
        val tekst: String,
        val idMace: String,
        var slikaMace: String? = "",
        val odgovori: List<String>,
        val tacanOdgovor: String

    )

    data class PitanjeState(
        val ucitavanje: Boolean = true,
        val prikaziOdg: Boolean = false,
        val kraj: Boolean = false,

        val pitanja: List<Pitanje> = emptyList(),
        val trenutnoPitanje: Int = 0,
        val tacniOdgovori: Int = 0,
        val preostaloVreme: Long = 0L,

        val rezultat: Double = 0.0,
        val error: KvizError? = null,

        val isQuizRunning: Boolean = false
    ){
        sealed class KvizError {
            data class KvizPao(val cause: Throwable? = null) : KvizError()
        }
    }

    sealed class PitanjeEvent {
        data class sledecePitanje(val selected: String) : PitanjeEvent()
        data object Isteklo: PitanjeEvent()
        data class Potvrda(val score: Double) : PitanjeEvent()
    }

}