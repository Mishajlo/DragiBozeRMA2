package com.example.dragiboze.views.kviz

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.startKviz(
    route: String,
    onClick: () -> Unit
) = composable (
    route = route
) {


    KvizStart (onClick = onClick)

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun KvizStart(
    onClick: () -> Unit
){

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Pocni Kviz",
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
            Button(
                modifier = Modifier.padding(paddingValues),
                onClick = onClick,
            ) {
                Text(
                    text = "Zapocni Kviz",
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
    )

}
