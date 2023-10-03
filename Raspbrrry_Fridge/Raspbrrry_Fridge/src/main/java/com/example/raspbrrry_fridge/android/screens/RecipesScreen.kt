package com.example.raspbrrry_fridge.android.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.raspbrrry_fridge.android.components.GeneralScaffold
import com.example.raspbrrry_fridge.android.components.RecipeList

@Composable
fun RecipesScreen(
    navController: NavController
) {
    GeneralScaffold(navController){
        RecipeList(navController)
    }
}

@Preview(name = "RecipeScreen")
@Composable
private fun PreviewRecipesScreen() {
    RecipesScreen(rememberNavController())
}
