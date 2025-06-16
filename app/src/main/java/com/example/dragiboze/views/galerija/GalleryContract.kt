package com.example.dragiboze.views.galerija

import com.example.dragiboze.models.data.MacaDetailsModel
import com.example.dragiboze.models.data.SlikaUiModel

interface GalleryContract {

    data class UiState(
        val id_mace: String,
        val loading: Boolean = false,
        val error: errorOnData? = null,
        val data: List<SlikaUiModel> = emptyList<SlikaUiModel>()
    )

    sealed class errorOnData {
        data class dataFail(val error: Throwable? = null) : errorOnData()
    }

}