package com.example.dragiboze.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.dragiboze.views.details.maca
import com.example.dragiboze.views.detalji.korisnik
import com.example.dragiboze.views.galerija.galerimjau
import com.example.dragiboze.views.kviz.startKviz
import com.example.dragiboze.views.leaderboard.leaderboard
import com.example.dragiboze.views.macalist.mace
import com.example.dragiboze.views.registracija.registracija
import com.example.dragiboze.views.slicica.slicica
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MacaNavigation(){

    val navController = rememberNavController()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(

        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Prokleti Drawer",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("mace") {
                            popUpTo(0)
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Leaderboard") },
                    selected = false,
                    onClick = {
                        navController.navigate("takmicari")
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Kviz") },
                    selected = false,
                    onClick = {
                        navController.navigate("startKviz")
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Profil") },
                    selected = false,
                    onClick = {
                        navController.navigate("korisnik")
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Macplikacija") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Meni")
                        }
                    }
                )
            },
            content = { paddingValues ->
                Log.d("padding", paddingValues.toString())
                NavHost(
                    navController = navController,
                    startDestination = "registracija",
                ){
                    mace(
                        route = "mace",
                        onMacaClick = { id -> navController.navigate("mace/${id}") }
                    )
                    maca (
                        route = "mace/{id}",
                        onClose = { navController.navigateUp() },
                        galerijaKlik = {rasa, id -> navController.navigate("mjaulerija/${id}&${rasa}")}
                    )
                    galerimjau (
                        route = "mjaulerija/{id}&{rasa}",
                        onSlikaClick = { id, index -> navController.navigate("slicica/${id}&${index}") },
                        onClose = { navController.navigateUp() }
                    )
                    registracija (
                        route = "registracija",
                        onRegistration = { navController.navigate("mace") },
                        navController = navController
                    )
                    leaderboard (
                        route = "takmicari",
                    )
                    slicica (
                        route = "slicica/{id}&{index}",
                        onClose = { navController.navigateUp() }
                    )
                    startKviz (
                        route = "startKviz",
                        onClick = { navController.navigate("takmicari") }
                    )
                    korisnik(
                        route = "korisnik"
                    )
                }
            }
        )

    }
}