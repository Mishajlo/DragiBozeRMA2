package com.example.molimteboze.views.macalist

import com.example.molimteboze.models.data.MacaUiModel

interface MacaListScreenContract {

    data class UiState(
        val loading: Boolean = false,
        val data: List<MacaUiModel> = emptyList(),
        val error: errorOnData? = null,
        val query: String = ""
    )

    sealed class UiEvent {
        data class QuerySearch(val query: String): UiEvent()
        data object ClearSearch: UiEvent()
    }

    sealed class errorOnData {
        data class dataFail(val error: Throwable? = null) : errorOnData()
    }

}