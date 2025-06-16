package com.example.dragiboze.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.dragiboze.views.details.maca
import com.example.dragiboze.views.macalist.mace

@Composable
fun MacaNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "mace",
    ){
        mace(
            route = "mace",
            onMacaClick = { id -> navController.navigate("mace/${id}") }
        )
        maca (
            route = "mace/{id}",
            onClose = { navController.navigateUp() }
        )
    }
}