package com.example.raspbrrry_fridge.android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.raspbrrry_fridge.android.network.WebSocketClient
import com.example.raspbrrry_fridge.android.screens.*
import com.example.raspbrrry_fridge.android.ui.theme.MyCoolTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        val wsc=WebSocketClient()
        wsc.sendMessage("productInput")

        val notificationChannel = NotificationChannel(
            "fridge_notification",
            "Fridge",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)

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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent?.action == "open_recipes") {
            navController.navigate("RecipeCardsScreen") // Replace with your Composable destination ID
        }
    }

    @Composable
    fun Navigation() {
        navController = rememberNavController()

        NavHost(navController = navController, startDestination = "HomeScreen") {
            composable("HomeScreen") { HomeScreen(navController) }
            composable("ProduceScreen") { ProduceScreen(navController) }
            composable("RecipesScreen") { RecipesScreen(navController) }
            composable("ProfileScreen") { ProfileScreen(navController) }
            composable("ScanProduceScreen") { ScanProduceScreen(navController) }
            composable("SecretRecipeScreen") { SecretRecipeScreen(navController) }
            composable("RecipeCardsScreen") { RecipeCardsScreen(navController) }
        }
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
