package com.example.dragiboze.views.registracija

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.dragiboze.ui.theme.Lavander
import com.example.dragiboze.ui.theme.RoyalBlue
import com.example.dragiboze.ui.theme.Yellow
import com.example.dragiboze.views.macalist.MacaListScreenContract
import com.example.dragiboze.views.macalist.MaceListScreen
import com.example.dragiboze.views.registracija.RegistracijaContract.*

fun NavGraphBuilder.registracija(
    route: String,
    onRegistration: () -> Unit,
    navController: NavController
) = composable (
    route = route
) {

    val registracijaViewModel: RegistracijaViewModel = hiltViewModel()

    val state = registracijaViewModel.state.collectAsState()

    if (state.value.registrovan == true){
        navController.navigate("mace")
    }

    RegistracijaScreen(
        state = state.value,
        eventPublisher = { event ->
            registracijaViewModel.setEvent(event)
        },
        onRegistration = onRegistration
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistracijaScreen(
    state: UiState,
    eventPublisher: (uiEvent: UiEvent) -> Unit,
    onRegistration: () -> Unit
){


    if (state.registrovan == false){
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(
                        text = "Catapult Registration",
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

                    state.registrovan == true -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    else -> {
                        val outline = LocalFocusManager.current
                        Column(modifier = Modifier.padding(paddingValues)) {

                            OutlinedTextField(
                                value = state.username,
                                onValueChange = {
                                    eventPublisher(UiEvent.PromenaUsernamea(it))
                                },
                                label = { Text("Korisnicko Ime") },
                                singleLine = true,
                                trailingIcon = {
                                    IconButton(onClick = {
                                        eventPublisher(UiEvent.PromenaUsernamea(""))
                                        outline.clearFocus()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.Clear,
                                            contentDescription = "Brisi"
                                        )
                                    }
                                },
                                colors = TextFieldDefaults.colors(Yellow),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )

                            OutlinedTextField(
                                value = state.ime,
                                onValueChange = {
                                    eventPublisher(UiEvent.PromenaImena(it))
                                },
                                label = { Text("Ime") },
                                singleLine = true,
                                trailingIcon = {
                                    IconButton(onClick = {
                                        eventPublisher(UiEvent.PromenaImena(""))
                                        outline.clearFocus()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.Clear,
                                            contentDescription = "Brisi"
                                        )
                                    }
                                },
                                colors = TextFieldDefaults.colors(Yellow),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )

                            OutlinedTextField(
                                value = state.prezime,
                                onValueChange = {
                                    eventPublisher(UiEvent.PromenaPrezimena(it))
                                },
                                label = { Text("Prezime") },
                                singleLine = true,
                                trailingIcon = {
                                    IconButton(onClick = {
                                        eventPublisher(UiEvent.PromenaPrezimena(""))
                                        outline.clearFocus()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.Clear,
                                            contentDescription = "Brisi"
                                        )
                                    }
                                },
                                colors = TextFieldDefaults.colors(Yellow),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )

                            OutlinedTextField(
                                value = state.email,
                                onValueChange = {
                                    eventPublisher(UiEvent.PromenaEmail(it))
                                },
                                label = { Text("Email") },
                                singleLine = true,
                                trailingIcon = {
                                    IconButton(onClick = {
                                        eventPublisher(UiEvent.PromenaEmail(""))
                                        outline.clearFocus()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.Clear,
                                            contentDescription = "Brisi"
                                        )
                                    }
                                },
                                colors = TextFieldDefaults.colors(Yellow),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )

                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onClick = {
                                    eventPublisher(UiEvent.Register)
                                    onRegistration()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = RoyalBlue,
                                    contentColor = Lavander
                                )
                            ) {
                                Icon(Icons.Filled.Cake, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("PRIJAVI SE")
                            }

                        }
                    }
                }
            }
        )
    }

}