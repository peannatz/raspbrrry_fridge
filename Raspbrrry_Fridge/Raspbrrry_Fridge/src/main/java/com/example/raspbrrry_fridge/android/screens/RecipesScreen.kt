package com.example.raspbrrry_fridge.android.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.raspbrrry_fridge.android.components.GeneralScaffold

@Composable
fun RecipesScreen(
    navController: NavController
) {
    GeneralScaffold(navController){
        Column(Modifier.fillMaxSize()) {
            Text(modifier = Modifier.align(Alignment.CenterHorizontally), text = "RecipesScreen")
        }
    }
}

@Preview(name = "RecipeScreen")
@Composable
private fun PreviewRecipesScreen() {
    RecipesScreen(rememberNavController())
}
