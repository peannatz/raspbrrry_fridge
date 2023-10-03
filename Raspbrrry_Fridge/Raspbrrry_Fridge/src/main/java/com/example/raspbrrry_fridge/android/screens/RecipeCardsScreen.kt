package com.example.raspbrrry_fridge.android.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun RecipeCardsScreen(
    navController: NavController
) {
    Box() {
        Text(text = "RecipeCardsScreen")
    }
}

@Preview(name = "RecipeCardsScreen")
@Composable
private fun PreviewRecipeCardsScreen() {
    RecipeCardsScreen(rememberNavController())
}
