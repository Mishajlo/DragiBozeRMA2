package com.example.dragiboze.views.galerija

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragiboze.mappers.asSlikaUiModel
import com.example.dragiboze.models.repository.interfaces.MjauRepository
import com.example.dragiboze.views.galerija.GalleryContract.UiState
import com.example.dragiboze.views.galerija.GalleryContract.errorOnData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: MjauRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(UiState(savedStateHandle.get<String>("id") ?: ""))
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.update(reducer)

    init {
        ucitajMacu(savedStateHandle.get<String>("id") ?: "")
    }

    private fun ucitajMacu(id: String){
        viewModelScope.launch {
            setState { copy(loading = true) }

            try {
                val maca = repository.observeImagesForMaca(id).collect { setState {
                    copy(
                        data = it.map { it.asSlikaUiModel() },
                        error = null
                    )
                } }


            }catch (error: Exception){
                setState { copy(error = errorOnData.dataFail(error)) }
                Log.e("Puce Puska", "Error", error)
            }finally {
                setState { copy(loading = false) }
            }
        }
    }

}