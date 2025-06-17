package com.example.dragiboze.views.detalji

import com.example.dragiboze.database.entities.IgraDbModel
import com.example.dragiboze.datastore.UserData
import kotlinx.serialization.InternalSerializationApi

interface DetaljiContract {

    @OptIn(InternalSerializationApi::class)
    data class UiState(
        var loading: Boolean = true,
        val data: UserData = UserData(),
        val ukupneIgre: Int = 0,
        val rezultati: List<IgraDbModel> = emptyList(),
        val najboljiRank: Pair<Int, Double> = Pair(-1, -1.0),
        val error: errorOnData.dataFail? = null,
    )

    sealed class errorOnData {
        data class dataFail(val error: Throwable? = null) : errorOnData()
    }

}