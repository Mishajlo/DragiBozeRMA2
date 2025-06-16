package com.example.molimteboze.views.macadetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.molimteboze.models.api.model.MacApiModel
import com.example.molimteboze.models.data.DebelaMaca
import com.example.molimteboze.models.data.MacaDetailsModel
import com.example.molimteboze.models.data.MackicaSlicica
import com.example.molimteboze.models.repository.MacaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.molimteboze.views.macadetails.MacaDetailsScreenContract.UiState
import com.example.molimteboze.views.macadetails.MacaDetailsScreenContract.errorOnData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@HiltViewModel
class MacaDetailsViewModel @Inject constructor(
    private val repository: MacaRepository,
    savedStateHandle: SavedStateHandle
): ViewModel(){

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
                val maca = withContext(Dispatchers.IO) {
                    repository.getMaca(id)
                }

                val url = maca?.image?.url ?: maca?.reference_image_id?.let {
                    try {
                        val slika = withContext(Dispatchers.IO) {
                            repository.getImage(it)
                        }
                        slika.url
                    }catch (error: Exception){
                        setState { copy(error = errorOnData.dataFail(error)) }
                        Log.e("Slika Umrla", "Error", error)
                    }
                }

                val uiMaca = maca?.asUserUiModel(url.toString())

                setState {
                    copy(
                        data = uiMaca,
                        img = url.toString(),
                        error = null
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
    private fun MacApiModel.asUserUiModel(url: String) = MacaDetailsModel(
        id_mace = id,
        slika_mace = MackicaSlicica(
            url = image?.url ?: "",
            width = image?.width ?: 0,
            height = image?.height ?: 0,
            id = image?.id ?: ""
        ),
        url_mace = url,
        ime_rase = name,
        opis = description,
        poreklo = origin,
        temperament = temperament.split(","),
        zivot = life_span,
        debel = DebelaMaca(
            imperial = weight.imperial,
            metric = weight.metric
        ),
        affection_level = affection_level,
        intelligence = intelligence,
        child_friendly = child_friendly,
        dog_friendly = dog_friendly,
        stranger_friendly = stranger_friendly,
        retkost = rare,
        wikipedia = wikipedia_url.toString()
    )
}