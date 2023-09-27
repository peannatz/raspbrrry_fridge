package com.example.raspbrrry_fridge.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.composable
import com.example.raspbrrry_fridge.android.screens.*
import com.example.raspbrrry_fridge.android.ui.theme.MyCoolTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCoolTheme(useDarkTheme = isSystemInDarkTheme()) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen"){ HomeScreen(navController) }
        composable("ProduceScreen"){ ProduceScreen(navController)}
        composable("RecipesScreen"){ RecipesScreen(navController)}
        composable("StatsScreen"){ StatsScreen(navController)}
        composable("ScanProduceScreen"){ ScanProduceScreen(navController)}
        composable("ProduceInputScreen"){ ProduceInputScreen(navController)}

    }
}


@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyCoolTheme(useDarkTheme = isSystemInDarkTheme()) {
        GreetingView("Hello, Android!")
    }
}
