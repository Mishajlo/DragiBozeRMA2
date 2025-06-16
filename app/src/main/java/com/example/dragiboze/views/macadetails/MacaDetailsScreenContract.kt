package com.example.molimteboze.views.macadetails

import com.example.dragiboze.models.data.MacaDetailsModel

interface MacaDetailsScreenContract {

    data class UiState(
        val id_mace: String,
        val loading: Boolean = false,
        val data: MacaDetailsModel? = null,
        val error: errorOnData? = null,
        val img: String? = null
    )

    sealed class errorOnData {
        data class dataFail(val error: Throwable? = null) : errorOnData()
    }

}