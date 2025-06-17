package com.example.dragiboze.views.detalji

import android.util.Log
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.dragiboze.ui.theme.Accent
import com.example.dragiboze.ui.theme.Lavander
import com.example.dragiboze.ui.theme.RoyalBlue
import com.example.dragiboze.ui.theme.Yellow
import com.example.dragiboze.views.detalji.DetaljiContract.errorOnData
import com.example.dragiboze.views.galerija.GalleryContract

fun NavGraphBuilder.korisnik(
    route: String
) = composable (
    route = route
) {

    val detaljiViewModel: DetaljiViewModel = hiltViewModel()

    val state = detaljiViewModel.state.collectAsState()


    KorisnikDetalji(
        state = state.value
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun KorisnikDetalji(
    state: DetaljiContract.UiState
){

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = state.data.korisnickoIme)
                },
            )
        },
        content = { paddingValues ->

            when {
                state.error != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        val greska = when (state.error) {
                            is errorOnData.dataFail ->
                                "Nesto je puklooo. Error kaze: ${state.error.error?.message}"
                        }
                        Text(text = greska)
                    }
                }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Ime: ${state.data.ime}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Yellow
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Prezime: ${state.data.prezime}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Lavander
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Email: ${state.data.email}",
                            style = MaterialTheme.typography.titleMedium,
                            color = Lavander
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Najbolji rang: ${state.najboljiRank.first} - Najbolji skor: ${state.najboljiRank.second}",
                            style = MaterialTheme.typography.titleMedium,
                            color = RoyalBlue
                        )
                        Text(
                            text = "Ukupan broj igara: ${state.ukupneIgre}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Lavander
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                    ) {
                        Text(
                            text = "Istorija Igara:",
                            style = MaterialTheme.typography.titleMedium,
                            color = Accent
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {

                            items(
                                items = state.rezultati,
                            ) { igra ->
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
                                    ) {
                                        Row {
                                            Text(
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .align(Alignment.CenterVertically),
                                                text = state.rezultati.indexOf(igra).toString(),
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
                                                    .padding(4.dp),
                                                text = "Poeni: ${igra.rezultat}",
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
                }}
            }

        }
    )


}