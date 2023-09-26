package com.example.raspbrrry_fridge.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.composable
import com.example.raspbrrry_fridge.android.screens.HomeScreen
import com.example.raspbrrry_fridge.android.screens.ProduceScreen
import com.example.raspbrrry_fridge.android.screens.RecipesScreen
import com.example.raspbrrry_fridge.android.screens.StatsScreen
import com.example.raspbrrry_fridge.android.screens.ScanProduceScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
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
    }
}


@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
