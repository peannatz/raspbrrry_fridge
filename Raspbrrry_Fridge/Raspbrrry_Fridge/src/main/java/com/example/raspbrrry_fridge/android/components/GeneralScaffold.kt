package com.example.raspbrrry_fridge.android.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun GeneralScaffold(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = { NavBar(navController) },
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        floatingActionButton = { QuickScanButton(navController) },
        content = { paddingValues ->
            // The content lambda allows you to add your screen content
            // You can pass it as a parameter when using CustomScaffold
            content(paddingValues)
        },
    )
}

@Preview(name = "GeneralScaffold")
@Composable
private fun PreviewGeneralScaffold() {
    GeneralScaffold(rememberNavController(), {})
}
