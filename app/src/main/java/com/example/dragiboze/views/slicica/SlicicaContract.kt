package com.example.dragiboze.views.slicica

import com.example.dragiboze.models.data.SlikaUiModel

interface SlicicaContract {

    data class UiState(
        val trenutna: Int = 0,
        val loading: Boolean = false,
        val error: errorOnData? = null,
        val data: List<SlikaUiModel> = emptyList<SlikaUiModel>()
    )

    sealed class errorOnData {
        data class dataFail(val error: Throwable? = null) : errorOnData()
    }

}