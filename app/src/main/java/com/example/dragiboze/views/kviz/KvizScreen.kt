package com.example.dragiboze.views.kviz

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.dragiboze.views.kviz.KvizContract.*
import com.example.dragiboze.views.kviz.KvizContract.UiState.errorOnData
import com.example.dragiboze.views.macalist.MacaListScreenContract
import kotlinx.coroutines.delay

fun NavGraphBuilder.kviz(
    route: String,
    onClick: () -> Unit
) = composable (
    route = route
) {

    val kvizViewModel: KvizViewModel = hiltViewModel<KvizViewModel>()

    val state = kvizViewModel.state.collectAsState()

    KvizScreen (
        state = state.value,
        eventPublisher = { event ->
            kvizViewModel.setEvent(event)
        },
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun KvizScreen(
    state: UiState,
    eventPublisher: (uiEvent: UiEvent) -> Unit,
){

    Scaffold (
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Kvizz",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Cursive
                    )
                )

            })
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {

                when{
                    state.error != null -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center,
                        ){
                            val greska = when(state.error){
                                is errorOnData.dataFail ->
                                    "Nesto je puklooo. Error kaze: ${state.error.cause}"
                            }
                            Text(text = greska)
                        }
                    }

                    state.ucitavanje -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            CircularProgressIndicator()
                        }
                    }

                    else -> {

                        val question = state.pitanja[state.trenutnoPitanje]
                        var answer = rememberSaveable { mutableStateOf("") }
                        var showCancelDialog = rememberSaveable { mutableStateOf(false) }
                        var isQuestionVisible = remember { mutableStateOf(false) }

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.TopCenter
                        ){
                            Text(text = "")
                        }


                    }
                }

            }

        }
    )

}