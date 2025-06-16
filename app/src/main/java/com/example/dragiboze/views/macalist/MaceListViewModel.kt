package com.example.molimteboze.views.macalist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.molimteboze.models.api.model.MacApiModel
import com.example.molimteboze.models.data.MacaUiModel
import com.example.molimteboze.models.repository.MacaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.molimteboze.views.macalist.MacaListScreenContract.UiState
import com.example.molimteboze.views.macalist.MacaListScreenContract.UiEvent
import com.example.molimteboze.views.macalist.MacaListScreenContract.errorOnData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext


@HiltViewModel
class MaceListViewModel @Inject constructor(
    private val repository: MacaRepository
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.update(reducer)

    private val events = MutableSharedFlow<UiEvent>()
    fun setEvent(event: UiEvent) = viewModelScope.launch { events.emit(event) }

    init{
        sveMace()
        observeEvents()
    }

    private fun observeEvents(){
        viewModelScope.launch {
            events.collect { event ->
                when(event){
                    is UiEvent.QuerySearch ->  { filterMace(event.query) }
                    is UiEvent.ClearSearch -> {
                        setState { copy(query = "") }
                        sveMace()
                    }
                }
            }
        }
    }

    private fun sveMace(){
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                val mace = withContext(Dispatchers.IO) {
                    repository.getAllMace().map { it.asUserUiModel() }
                }
                setState { copy(data = mace) }
            }catch (error: Exception){
                setState { copy(error = errorOnData.dataFail(error)) }
                Log.e("Puce Puska", "Error", error)
            }finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun filterMace(query: String){
        val filtriraneMace = _state.value.data.filter { maca ->
            maca.ime_rase.startsWith(query, ignoreCase = true)
        }
        Log.e("Query", query)
        setState { copy(data = filtriraneMace, query = query) }
    }

    private fun MacApiModel.asUserUiModel() = MacaUiModel(
        id_mace = id,
        ime_rase = name,
        alt_ime = alt_names,
        opis = description,
        temperament = temperament.split(",")
    )

}