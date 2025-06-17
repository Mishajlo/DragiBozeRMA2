package com.example.dragiboze.views.registracija

import com.example.dragiboze.datastore.UserData

interface RegistracijaContract {

    data class UiState(
        val registrovan: Boolean = false,
        val ime: String = "",
        val prezime: String = "",
        val email: String = "",
        val username: String = "",
        val error: errorOnData? = null,
    )

    sealed class UiEvent {
        data object Register: UiEvent()
        data class PromenaImena(val ime: String): UiEvent()
        data class PromenaPrezimena(val prezime: String): UiEvent()
        data class PromenaUsernamea(val username: String): UiEvent()
        data class PromenaEmail(val email: String): UiEvent()
    }


    sealed class errorOnData {
        data class dataFail(val error: Throwable? = null) : errorOnData()
    }

}