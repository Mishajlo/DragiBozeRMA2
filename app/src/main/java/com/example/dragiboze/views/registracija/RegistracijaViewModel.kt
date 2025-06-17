package com.example.dragiboze.views.registracija

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragiboze.datastore.UserData
import com.example.dragiboze.models.repository.interfaces.KorisnikRepository
import com.example.dragiboze.views.macalist.MacaListScreenContract
import com.example.meoworld.data.datastore.UserStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.dragiboze.views.registracija.RegistracijaContract.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RegistracijaViewModel @Inject constructor(
    private val korisnikRepository: KorisnikRepository
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.update(reducer)

    private val events = MutableSharedFlow<UiEvent>()
    fun setEvent(event: UiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        provera()
        observeEvents()
    }

    private fun provera(){
        viewModelScope.launch {
            if (korisnikRepository.isRegistered() == false){
                setState { copy( registrovan = true) }
            }
        }
    }

    private fun observeEvents(){
        viewModelScope.launch {
            events.collect { event ->
                when(event){
                    is UiEvent.PromenaImena -> { setState { copy( ime = event.ime) } }
                    is UiEvent.PromenaPrezimena -> { setState { copy( prezime = event.prezime) } }
                    is UiEvent.PromenaEmail -> { setState { copy( email = event.email) } }
                    is UiEvent.PromenaUsernamea -> { setState { copy( username = event.username) } }
                    is UiEvent.Register -> {
                        if (!state.value.ime.isEmpty() && !state.value.prezime.isEmpty() && !state.value.email.isEmpty() && !state.value.username.isEmpty()){
                            try {
                                korisnikRepository.register(UserData(
                                    ime = state.value.ime,
                                    prezime = state.value.prezime,
                                    email = state.value.email,
                                    korisnickoIme = state.value.username
                                ))

                                setState { copy( registrovan = true, error = null ) }
                            }catch (error: Exception){
                                setState { copy(error = errorOnData.dataFail(error), registrovan = false) }
                                Log.e("Puce Puska", "Error", error)
                            }
                        }

                        Log.d("Ime", state.value.ime)
                        Log.d("Prezime", state.value.prezime)
                        Log.d("Email", state.value.email)
                        Log.d("Username", state.value.username)
                    }
                }
            }
        }
    }

}