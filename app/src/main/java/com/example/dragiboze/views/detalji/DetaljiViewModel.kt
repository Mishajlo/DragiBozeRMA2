package com.example.dragiboze.views.detalji

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragiboze.models.repository.interfaces.KorisnikRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.dragiboze.views.detalji.DetaljiContract.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class DetaljiViewModel @Inject constructor(
    private val korisnikRepository: KorisnikRepository
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()

    private fun setState(reducer: UiState.() -> UiState) = _state.update(reducer)

    init {
        korisnik()
    }

    private fun korisnik() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try{
                    val userData = korisnikRepository.getUserData()
                    val rezultati = korisnikRepository.getUserIgre()
                    val rang = korisnikRepository.getNo1()

                    setState {
                        copy(
                            data = userData,
                            ukupneIgre = rezultati.size,
                            rezultati = rezultati,
                            najboljiRank = rang
                        )
                    }
                }catch (error: Exception){
                    setState { copy(error = errorOnData.dataFail(error)) }
                    Log.e("Puce Puska", "Error", error)
                }finally {
                    setState { copy(loading = false) }
                }
            }
        }
    }

}