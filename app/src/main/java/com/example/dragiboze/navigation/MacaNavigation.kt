package com.example.dragiboze.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun MacaNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "mace",
    ){}
}