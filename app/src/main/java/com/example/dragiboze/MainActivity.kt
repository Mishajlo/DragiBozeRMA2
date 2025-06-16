package com.example.dragiboze

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dragiboze.navigation.MacaNavigation
import com.example.dragiboze.ui.theme.DragiBozeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DragiBozeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    Log.d("Padding", padding.toString())
                    setContent {
                        MacaNavigation()
                    }
                }
            }
        }
    }
}
