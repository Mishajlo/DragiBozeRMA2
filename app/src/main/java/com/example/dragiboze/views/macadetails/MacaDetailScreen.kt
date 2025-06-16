package com.example.molimteboze.views.macadetails

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil3.compose.AsyncImage

fun NavGraphBuilder.maca(
    route: String,
    onClose: () -> Unit
) = composable (
    route = route
) {
    val macaId = it.arguments?.getString("id") ?: ""

    if (macaId.isEmpty()){
        throw IllegalStateException("Za Cecu nema")
    }

    val macaDetailsViewModel: MacaDetailsViewModel = hiltViewModel()

    val state = macaDetailsViewModel.state.collectAsState()


    MackicaDetail(
        state = state.value,
        onClose = onClose
    )

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MackicaDetail(
    state: MacaDetailsScreenContract.UiState,
    onClose: () -> Unit
){
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = state.data?.ime_rase ?: "Ucitavanje")
                },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Byebye",
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when{
                    state.error != null -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.Center,
                        ){
                            val greska = when(state.error){
                                is MacaDetailsScreenContract.errorOnData.dataFail ->
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


                        if (state.data != null){
                            val zaWiki = LocalUriHandler.current
                            Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp)
                                        .padding(12.dp)
                                ) {
                                    Log.d("URL MACKE", state.data.url_mace ?: "")
                                    state.data?.url_mace.let {
                                        AsyncImage(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(10.dp))
                                                .border(
                                                    5.dp,
                                                    Color.Red,
                                                    RoundedCornerShape(10.dp)
                                                ),
                                            model = it,
                                            contentDescription = state.data.ime_rase,
                                            contentScale = ContentScale.Fit
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(5.dp)
                                ) {
                                    Text(
                                        text = "O Maci:",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Red
                                    )
                                    Text(
                                        text = state.data.opis,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Black
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(5.dp)
                                ) {
                                    Text(
                                        text = "Temperament:",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Red
                                    )
                                    if (!state.data.temperament.isEmpty()) {
                                        state.data.temperament.forEach { trait ->
                                            SuggestionChip(
                                                modifier = Modifier
                                                    .padding(horizontal = 2.dp)
                                                    .padding(vertical = 2.dp),
                                                onClick = { Log.d("Trait", trait) },
                                                label = { Text(trait) }
                                            )
                                        }
                                    }
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(5.dp)
                                ) {
                                    Text(
                                        text = "Poreklo:",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Red
                                    )
                                    Text(
                                        text = state.data.poreklo,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Black
                                    )
                                }
                                if (state.data.retkost != 0) {
                                    Column(
                                        modifier = Modifier
                                            .padding(5.dp)
                                    ) {
                                        Text(
                                            text = "Retkost: ${state.data.retkost}/5",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.Blue
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(5.dp)
                                ) {
                                    Text(
                                        text = "Koliko dugo zive?",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Red
                                    )
                                    Text(
                                        text = state.data.zivot + " godina",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Black
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(5.dp)
                                ) {
                                    Text(
                                        text = "Koliko debelo:",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Red
                                    )
                                    Text(
                                        text = state.data.debel.metric + "kg",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.Black
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))

                                Column(
                                    modifier = Modifier
                                        .padding(5.dp)
                                ) {
                                    Text(
                                        text = "Pamet: " + state.data.intelligence + "/5",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Red
                                    )
                                    LinearProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        color = Color.Green,
                                        trackColor = Color.DarkGray,
                                        strokeCap = StrokeCap.Square,
                                        progress = { state.data.intelligence / 5f }
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(5.dp)
                                ) {
                                    Text(
                                        text = "Pristrasnost: " + state.data.affection_level + "/5",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Red
                                    )
                                    LinearProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        color = Color.Green,
                                        trackColor = Color.DarkGray,
                                        strokeCap = StrokeCap.Square,
                                        progress = { state.data.affection_level / 5f }
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .padding(5.dp)
                                ) {
                                    Text(
                                        text = "Dobre sa psima: " + state.data.dog_friendly + "/5",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Red
                                    )
                                    LinearProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        color = Color.Green,
                                        trackColor = Color.DarkGray,
                                        strokeCap = StrokeCap.Square,
                                        progress = { state.data.dog_friendly / 5f }
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(5.dp)
                                ) {
                                    Text(
                                        text = "Dobre sa strancima: " + state.data.stranger_friendly + "/5",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Red
                                    )
                                    LinearProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        color = Color.Green,
                                        trackColor = Color.DarkGray,
                                        strokeCap = StrokeCap.Square,
                                        progress = { state.data.stranger_friendly / 5f }
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(5.dp)
                                ) {
                                    Text(
                                        text = "Dobre sa decom: " + state.data.child_friendly + "/5",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Red
                                    )
                                    LinearProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        color = Color.Green,
                                        trackColor = Color.DarkGray,
                                        strokeCap = StrokeCap.Square,
                                        progress = { state.data.child_friendly / 5f }
                                    )
                                }

                                Spacer(modifier = Modifier.height(15.dp))

                                state.data.wikipedia.let {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(5.dp),
                                        contentAlignment = Alignment.Center
                                    ){
                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            onClick = { zaWiki.openUri(it) },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.Green,
                                                contentColor = Color.Red
                                            )
                                        ) {
                                            Icon(Icons.Filled.Info, contentDescription = null)
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("Learn More on Wikipedia")
                                        }
                                    }
                                }
                            }


                            //kraj
                        } else{
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues),
                                contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = "Ko ti je sad ova",
                                    fontSize = 32.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}