package com.example.raspbrrry_fridge.android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.raspbrrry_fridge.android.viewModel.ProductViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.raspbrrry_fridge.android.data.Recipe
import com.example.raspbrrry_fridge.android.viewModel.RecipeViewModel

@Composable
fun RecipeList(navController: NavController, rvm: RecipeViewModel = viewModel()) {

    rvm.getAllRecipes()
    val products: List<Recipe> by rvm.recipes.collectAsState()
    val typography = MaterialTheme.typography
    val interactionSource = remember { MutableInteractionSource() }

    Column(Modifier.padding(bottom = 80.dp)) {
        Row(Modifier.background(MaterialTheme.colorScheme.onPrimary)) {
            Spacer(Modifier.width(125.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable(indication = null, interactionSource = interactionSource) {
                        val secretActivated = rvm.handleSecretButtonPress()
                        if (secretActivated) {
                            navController.navigate("SecretRecipeScreen")
                        }
                    }
            ) {
                Text(text = "Recipe", Modifier.align(Alignment.TopStart), style = typography.titleMedium)
                Text(text = "Portions", Modifier.align(Alignment.CenterEnd), style = typography.labelMedium)
            }
        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inversePrimary)
        ) {
            items(products) { item ->
                Row(
                    Modifier
                        .padding(4.dp, 2.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable { navController.navigate("RecipeCardsScreen") },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
//                    AsyncImage(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data(item.url)
//                            .build(), contentDescription = "${item.name} Image",
//                        Modifier
//                            .height(100.dp)
//                            .width(100.dp)
//                    )

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(text = item.name, Modifier.align(Alignment.TopStart), style = typography.titleMedium)
                        Text(
                            text = item.portions.toString(),
                            Modifier.align(Alignment.CenterEnd),
                            style = typography.bodyMedium
                        )
                        //   Text(text = item.ecoscore_grade, Modifier.align(Alignment.BottomStart), style = normalStyle)
                        // Text(text = item.nutriscore_grade, Modifier.align(Alignment.BottomCenter), style = normalStyle)
                        // Text(text = item.nova_group, Modifier.align(Alignment.BottomEnd), style = normalStyle)
                    }
                }
            }
        }
    }

}

@Preview(name = "ProduceItems")
@Composable
private fun PreviewProduceItems() {
    val pvm = ProductViewModel()
    pvm.fetchProducts()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ProduceItems(pvm)
    }
}
