package com.example.dragiboze.views.details

import com.example.dragiboze.models.data.MacaDetailsModel

interface MacaDetailsContract {
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