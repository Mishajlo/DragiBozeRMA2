package com.example.dragiboze.views.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragiboze.database.entities.MacaDbModel
import com.example.dragiboze.models.data.DebelaMaca
import com.example.dragiboze.models.data.MacaDetailsModel
import com.example.dragiboze.models.data.MackicaSlicica
import com.example.dragiboze.models.repository.interfaces.MjauRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.dragiboze.views.details.MacaDetailsScreenContract.UiState
import com.example.dragiboze.views.details.MacaDetailsScreenContract.errorOnData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@HiltViewModel
class MacaDetailsViewModel @Inject constructor(
    private val repository: MjauRepository,
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
                val maca = repository.obsereveMaca(id).firstOrNull()
                Log.d("Maca", maca?.name ?: "Nema")
                if (maca != null){
                    val uiMaca = maca.asUserUiModel()
                    Log.d("ImgURL", maca.imageUrl)
                    setState {
                        copy(
                            data = uiMaca,
                            img = uiMaca.url_mace,
                            error = null
                        )
                    }
                }else{
                    setState {
                        copy(
                            data = null,
                            img = null,
                            error = errorOnData.dataFail(NullPointerException())
                        )
                    }
                }

            }catch (error: Exception){
                setState { copy(error = errorOnData.dataFail(error)) }
                Log.e("Puce Puska", "Error", error)
            }finally {
                setState { copy(loading = false) }
            }
        }
    }
    private fun MacaDbModel.asUserUiModel() = MacaDetailsModel(
        id_mace = id,
        slika_mace = MackicaSlicica(
            url = "",
            width = 0,
            height = 0,
            id = ""
        ),
        url_mace = imageUrl,
        ime_rase = name,
        opis = description,
        poreklo = origin,
        temperament = temperament.split(","),
        zivot = life_span,
        debel = DebelaMaca(
            imperial = imperial,
            metric = metric
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