package com.example.dragiboze.views.kviz

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragiboze.models.repository.interfaces.KvizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.dragiboze.views.kviz.KvizContract.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class KvizViewModel @Inject constructor(
    private val kvizRepository: KvizRepository,
    private val takmicarRepository: KvizRepository
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.update(reducer)

    private val events = MutableSharedFlow<UiEvent>()
    fun setEvent(event: UiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeEvents()
        kreirajPitanja()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect { event ->
                when (event) {
                    is UiEvent.sledecePitanje -> {
                        val trenutno = state.value.trenutnoPitanje
                        val pitanja = state.value.pitanja

                        try {
                            val currentQuestion = pitanja.getOrNull(trenutno)
                            if (event.selected == currentQuestion?.tacanOdgovor) {
                                setState { copy(tacniOdgovori = tacniOdgovori + 1) }
                            }

                            if (trenutno < pitanja.size) {
                                val sledece = trenutno + 1
                                setState { copy(trenutnoPitanje = sledece) }
                            } else {
                                kraj()
                            }
                        } catch (e: Exception) {
                            setState { copy(error = UiState.errorOnData.dataFail(e)) }
                        }
                    }

                    is UiEvent.Isteklo -> kraj()

                    is UiEvent.Potvrda -> {
                        viewModelScope.launch {
                            try {
                                takmicarRepository.objaviRez(event.score)
                            } catch (e: Exception) {
                                setState { copy(error = UiState.errorOnData.dataFail(e)) }
                            }
                        }
                    }

                    is UiEvent.Promena -> {
                        viewModelScope.launch {
                            setState { copy(preostaloVreme = preostaloVreme - event.promena) }
                        }
                    }
                }
            }
        }
    }

    private fun kreirajPitanja() {
        viewModelScope.launch {
            setState { copy(ucitavanje = true, error = null, uToku = true) }

            try {
                val pitanja = withContext(Dispatchers.IO) {
                    kvizRepository.pitanja()
                }

                setState {
                    copy(pitanja = pitanja, ucitavanje = false)
                }

                state.value.tajmer.start()

            } catch (e: Exception) {
                setState { copy(ucitavanje = false, error = UiState.errorOnData.dataFail(e)) }
            }
        }
    }


    private suspend fun kraj() {
        try {
            state.value.tajmer.cancel()
            setState { copy(kraj = true, uToku = false) }

            val skor = skor()
            setState { copy(rezultat = skor) }

            try {
                kvizRepository.objaviRez(skor)
            } catch (e: Exception) {
                setState { copy(error = UiState.errorOnData.dataFail(e)) }
            }

        } catch (e: Exception) {
            setState { copy(error = UiState.errorOnData.dataFail(e)) }
        }
    }

    private fun skor(): Double {
        return try {
            val tacniOdg = state.value.tacniOdgovori
            val celoVreme = 300
            val preostaloVreme = state.value.preostaloVreme.toInt()
            var rezultat = (tacniOdg * 2.5 * (1 + (preostaloVreme + 120) / celoVreme))
            if (rezultat > 100){
                rezultat = 100.0
            }
            return rezultat
        } catch (e: Exception) {
            setState { copy(error = UiState.errorOnData.dataFail(e)) }
            0.0
        }
    }
}