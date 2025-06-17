package com.example.dragiboze.views.kviz

import android.os.CountDownTimer

interface KvizContract {

    data class Pitanje(
        val tekst: String,
        val idMace: String,
        var slikaMace: String? = "",
        val odgovori: List<String>,
        val tacanOdgovor: String

    )

    data class UiState(
        val ucitavanje: Boolean = true,
        val prikaziOdg: Boolean = false,
        val kraj: Boolean = false,

        val pitanja: List<Pitanje> = emptyList(),
        val trenutnoPitanje: Int = 0,
        val tacniOdgovori: Int = 0,
        val preostaloVreme: Long = 0L,
        val tajmer: CountDownTimer = object:  CountDownTimer( 300000000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                UiEvent.Promena(millisUntilFinished)
            }

            override fun onFinish() {
                UiEvent.Isteklo
            }

        },

        val rezultat: Double = 0.0,
        val error: errorOnData? = null,

        val uToku: Boolean = false
    ){
        sealed class errorOnData {
            data class dataFail(val cause: Throwable? = null) : errorOnData()
        }
    }

    sealed class UiEvent {
        data class sledecePitanje(val selected: String) : UiEvent()
        data object Isteklo: UiEvent()
        data class Potvrda(val score: Double) : UiEvent()
        data class Promena(val promena: Long): UiEvent()
    }

}