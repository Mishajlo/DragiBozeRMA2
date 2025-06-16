package com.example.dragiboze.views.macalist

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.molimteboze.views.macalist.MaceListViewModel

fun NavGraphBuilder.mace(
    route: String,
    onMacaClick: (String) -> Unit
) = composable (
    route = route
) {

    val maceListViewModel: MaceListViewModel = hiltViewModel()

    val state = maceListViewModel.state.collectAsState()

    MaceListScreen(
        state = state.value,
        eventPublisher = { event ->
            maceListViewModel.setEvent(event)
        },
        onMacaClick = onMacaClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaceListScreen(
    state: MacaListScreenContract.UiState,
    eventPublisher: (uiEvent: MacaListScreenContract.UiEvent) -> Unit,
    onMacaClick: (String) -> Unit
){

    val outline = LocalFocusManager.current

    Scaffold (
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Svee Macee",
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

                OutlinedTextField(
                    value = state.query,
                    onValueChange = {
                        eventPublisher(MacaListScreenContract.UiEvent.QuerySearch(it))
                    },
                    label = {Text("Pretraga")},
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Pretraga"
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            eventPublisher(MacaListScreenContract.UiEvent.ClearSearch)
                            outline.clearFocus()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Brisi"
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                when{
                    state.error != null -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center,
                        ){
                            val greska = when(state.error){
                                is MacaListScreenContract.errorOnData.dataFail ->
                                    "Nesto je puklooo. Error kaze: ${state.error.error?.message}"
                            }
                            Text(text = greska)
                        }
                    }

                    state.loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ){
                            CircularProgressIndicator()
                        }
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {

                            items(
                                items = state.data,
                                key = { it.id_mace }
                            ) { maca ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp)
                                        .padding(18.dp)
                                        .clickable { onMacaClick(maca.id_mace) },
                                    colors = CardDefaults.cardColors()
                                ) {
                                    Column {
                                        Text(
                                            modifier = Modifier
                                                .align(Alignment.CenterHorizontally)
                                                .padding(top = 12.dp),
                                            text = maca.ime_rase,
                                            style = MaterialTheme.typography.headlineLarge.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Cursive
                                            )
                                        )

                                        if (!maca.alt_ime.isNullOrEmpty()){
                                            Text(
                                                modifier = Modifier
                                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                                                text = "A jos je zovu: ${maca.alt_ime}",
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }

                                        Text(
                                            modifier = Modifier
                                                .padding(horizontal = 16.dp, vertical = 4.dp),
                                            text = maca.opis.take(250),
                                            style = MaterialTheme.typography.bodyLarge
                                        )

                                        if (maca.temperament.isNotEmpty()){
                                            var brojac = 0
                                            maca.temperament.forEach { trait ->
                                                if (brojac < 5) {
                                                    Row {
                                                        SuggestionChip(
                                                            modifier = Modifier
                                                                .padding(horizontal = 2.dp)
                                                                .padding(vertical = 2.dp),
                                                            onClick = { Log.d("Trait", trait) },
                                                            label = { Text(trait) }
                                                        )
                                                    }
                                                }
                                                brojac++
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

            }

        }
    )

}

