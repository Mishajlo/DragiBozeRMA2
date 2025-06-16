package com.example.molimteboze.views.macalist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragiboze.models.repository.interfaces.MjauRepository
import com.example.dragiboze.views.macalist.MacaListScreenContract.UiEvent
import com.example.dragiboze.views.macalist.MacaListScreenContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import com.example.dragiboze.mappers.asMacaUiModel
import com.example.dragiboze.views.macalist.MacaListScreenContract.errorOnData


@HiltViewModel
class MaceListViewModel @Inject constructor(
    private val repository: MjauRepository
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.update(reducer)

    private val events = MutableSharedFlow<UiEvent>()
    fun setEvent(event: UiEvent) = viewModelScope.launch { events.emit(event) }

    init{
        sveMace()
        observeEvents()
        posmatrajMace()
    }

    private fun observeEvents(){
        viewModelScope.launch {
            events.collect { event ->
                when(event){
                    is UiEvent.QuerySearch ->  { filterMace(event.query) }
                    is UiEvent.ClearSearch -> {
                        setState { copy(query = "") }
                        posmatrajMace()
                    }
                }
            }
        }
    }



    private fun sveMace(){
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                withContext(Dispatchers.IO) {
                    repository.getAllMace()
                }


            }catch (error: Exception){
                setState { copy(error = errorOnData.dataFail(error)) }
                Log.e("Puce Puska", "Error", error)
            }finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun posmatrajMace(){
        viewModelScope.launch {
            repository.observeMace()
                .collect { mace ->
                    val mace = mace.map { it.asMacaUiModel() }
                    setState { copy(data = mace) }
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



}