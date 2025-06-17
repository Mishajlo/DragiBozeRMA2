package com.example.dragiboze.views.leaderboard

import com.example.dragiboze.models.data.TakmicarUiModel

interface LeaderboardContract {

    data class UiState(
        val loading: Boolean = false,
        val data: List<TakmicarUiModel> = emptyList(),
        val error: errorOnData? = null,
    )

    sealed class errorOnData {
        data class dataFail(val error: Throwable? = null) : errorOnData()
    }

}