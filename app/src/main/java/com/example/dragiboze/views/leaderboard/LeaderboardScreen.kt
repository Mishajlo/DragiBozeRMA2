package com.example.dragiboze.views.leaderboard

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.dragiboze.ui.theme.Yellow
import com.example.dragiboze.views.leaderboard.LeaderboardContract.*
import com.example.dragiboze.views.macalist.MacaListScreenContract

fun NavGraphBuilder.leaderboard(
    route: String
) = composable (
    route = route
) {


    val leaderboardViewModel: LeaderboardViewModel = hiltViewModel()

    val state = leaderboardViewModel.state.collectAsState()

    LeaderboardScreen(
        state = state.value
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LeaderboardScreen(
    state: UiState,
){

    Scaffold (
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Takmicari",
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
                                /*key = { it }*/
                            ) { takimcar ->
                                Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(7.dp),
                                colors = CardDefaults.cardColors(Yellow)
                            ) {
                                    FlowRow(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.CenterHorizontally)
                                            .padding(5.dp)
                                    ){
                                        Row {
                                            Text(
                                                modifier = Modifier
                                                    .padding(10.dp).align(Alignment.CenterVertically),
                                                text = state.data.indexOf(takimcar).toString(),
                                                style = MaterialTheme.typography.headlineLarge.copy(
                                                    fontWeight = FontWeight.Thin,
                                                    fontFamily = FontFamily.Cursive,
                                                    fontSize = 30.sp
                                                )
                                            )
                                        }
                                        Row {
                                            Text(
                                                modifier = Modifier
                                                    .padding(12.dp),
                                                text = takimcar.korisnik,
                                                style = MaterialTheme.typography.headlineLarge.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontFamily = FontFamily.Cursive,
                                                    fontSize = 40.sp
                                                )
                                            )
                                        }
                                        Row {
                                            Text(
                                                modifier = Modifier
                                                    .padding(4.dp),
                                                text = "Poeni: ${takimcar.rezultat}",
                                                style = MaterialTheme.typography.headlineLarge.copy(
                                                    fontWeight = FontWeight.Thin,
                                                    fontFamily = FontFamily.Cursive,
                                                    fontSize = 25.sp
                                                )
                                            )
                                        }
                                        Row {
                                            Text(
                                                modifier = Modifier
                                                    .padding(4.dp),
                                                text = "Broj igara: ${takimcar.ukupneIgre}",
                                                style = MaterialTheme.typography.headlineLarge.copy(
                                                    fontWeight = FontWeight.Thin,
                                                    fontFamily = FontFamily.Cursive,
                                                    fontSize = 25.sp
                                                )
                                            )
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