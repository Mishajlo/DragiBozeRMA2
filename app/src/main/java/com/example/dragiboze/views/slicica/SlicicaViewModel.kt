package com.example.dragiboze.views.slicica

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragiboze.mappers.asSlikaUiModel
import com.example.dragiboze.models.repository.interfaces.MjauRepository
import com.example.dragiboze.views.galerija.GalleryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.example.dragiboze.views.slicica.SlicicaContract.*
import kotlinx.coroutines.launch

@HiltViewModel
class SlicicaViewModel @Inject constructor(
    private val repository: MjauRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.update(reducer)

    init {
        ucitajMacu(savedStateHandle.get<String>("id") ?: "", (savedStateHandle.get<String>("index") ?: "").toInt())
    }

    private fun ucitajMacu(id: String, index: Int){
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                val slike = repository.getImagesForMaca(id).map { it.asSlikaUiModel() }
                setState { copy( data = slike, trenutna = index ) }

            }catch (error: Exception){
                setState { copy(error = errorOnData.dataFail(error)) }
                Log.e("Puce Puska", "Error", error)
            }finally {
                setState { copy(loading = false) }
            }
        }
    }
}