package com.example.raspbrrry_fridge.android.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.raspbrrry_fridge.android.R
import com.example.raspbrrry_fridge.android.ui.theme.MyCoolTheme

var selectedItem by mutableIntStateOf(0)

@Composable
fun NavBar(navController: NavController) {
    val items = listOf("Home", "Produce", "Recipes", "Stats")

    NavigationBar(containerColor = MaterialTheme.colorScheme.onPrimary) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = getDrawableResource(item)),
                        contentDescription = item,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                label = { Text(item, color = MaterialTheme.colorScheme.primary) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index;
                    navController.navigate("${item}Screen")
                }
            )
        }
    }
}

fun getDrawableResource(item: String): Int {
    return when (item) {
        "Home" -> R.drawable.home
        "Produce" -> R.drawable.nutrition
        "Recipes" -> R.drawable.recipes
        "Stats" -> R.drawable.stats
        else -> androidx.core.R.drawable.notify_panel_notification_icon_bg // Use a default icon for other cases
    }
}


@Preview(name = "NavBar")
@Composable
private fun PreviewNavBar() {
    NavBar(rememberNavController())
}
