package com.example.dragiboze.views.leaderboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragiboze.models.repository.interfaces.TakmicarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.dragiboze.views.leaderboard.LeaderboardContract.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.dragiboze.mappers.asTakmicarUiModel
import com.example.dragiboze.models.data.TakmicarUiModel

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val repository: TakmicarRepository
): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: UiState.() -> UiState) = _state.update(reducer)

    init{
        sviTakmicari()
    }

    private fun sviTakmicari(){
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                val svi = withContext(Dispatchers.IO) {
                    repository.sviTakmicari()
                }
                val takmicari = svi.map { it.asTakmicarUiModel(svi.groupingBy { it.nickname }.eachCount()[it.nickname]) }
                setState { copy(data = takmicari) }
            }catch (error: Exception){
                setState { copy(error = errorOnData.dataFail(error)) }
                Log.e("Puce Puska", "Error", error)
            }finally {
                setState { copy(loading = false) }
            }
        }
    }


}