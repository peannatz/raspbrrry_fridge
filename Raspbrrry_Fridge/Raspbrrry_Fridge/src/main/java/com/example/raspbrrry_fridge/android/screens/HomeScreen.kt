package com.example.raspbrrry_fridge.android.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.raspbrrry_fridge.android.components.GeneralScaffold


@Composable
fun HomeScreen(
    navController: NavController
) {
    GeneralScaffold(navController)
    { Column(Modifier.fillMaxSize()){
       // Image(painter = , contentDescription = )
            Text(text = "Welcome", modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(
                text = "You're all good, nothing wasted today.",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
    }
    }

}

@Preview(name = "MainScreen")
@Composable
private fun PreviewHomeScreen() {
    HomeScreen(rememberNavController())
}
