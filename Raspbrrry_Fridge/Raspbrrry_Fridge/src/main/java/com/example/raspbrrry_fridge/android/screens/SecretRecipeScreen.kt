package com.example.raspbrrry_fridge.android.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.raspbrrry_fridge.android.R
import com.example.raspbrrry_fridge.android.components.CustomButton
import com.example.raspbrrry_fridge.android.data.Recipe
import com.example.raspbrrry_fridge.android.viewModel.RecipeViewModel

@Composable
fun SecretRecipeScreen(
    navController: NavController,
    rvm: RecipeViewModel = viewModel()
) {
    val dropdownExpanded = remember { mutableStateOf(true) }
    val ingredientsList = remember { mutableStateListOf<Pair<String, String>>() }

    rvm.getAllRecipes()
    val recipes = rvm.recipes.collectAsState()

    fun getAllIngredientsAsStrings(): List<String> {
        return ingredientsList.map { "${it.first}:${it.second}" }
    }

    fun updateWeight(index: Int, newValue: String) {
        ingredientsList[index] = Pair(ingredientsList[index].first, newValue)
    }

    fun updateName(index: Int, newValue: String) {
        ingredientsList[index] = Pair(newValue, ingredientsList[index].second)
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .width(300.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Box(Modifier.width(300.dp)) {
                Text(
                    text = if (recipes.value.isNotEmpty()) {
                        "Select a Recipe to edit or add a new one"
                    } else {
                        "No Recipes yet, please add some"
                    },
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    modifier =
                    Modifier
                        .clickable(onClick = { dropdownExpanded.value = true })
                        .border(width = 2.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        .width(300.dp)
                        .height(50.dp)

                )
                DropdownMenu(
                    expanded = dropdownExpanded.value, onDismissRequest = { dropdownExpanded.value = false },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(300.dp)
                ) {
                    DropdownMenuItem(text = { Text("Add new Recipe") }, onClick = {
                        val newRecipe = Recipe()
                        rvm.selectRecipe(newRecipe)
                        dropdownExpanded.value = false
                    })
                    recipes.value.forEach { r ->
                        DropdownMenuItem(text = { Text(r.name) }, onClick = {
                            rvm.selectRecipe(r)
                            dropdownExpanded.value = false
                        })
                    }
                }
            }
            if (rvm.isSelectedRecipeInitialised()) {
                val recipeName = remember { mutableStateOf(rvm.selectedRecipe.name) }
                val recipePortions = remember { mutableStateOf(rvm.selectedRecipe.portions.toString()) }
                val recipeUrl = remember { mutableStateOf(rvm.selectedRecipe.url) }
                val recipeDescription = remember { mutableStateOf(rvm.selectedRecipe.description) }

                TextField(
                    modifier = Modifier.width(300.dp),
                    value = recipeName.value,
                    onValueChange = {
                        recipeName.value = it
                        rvm.selectedRecipe.name = it
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                    label = { Text("Name") }
                )
                TextField(
                    modifier = Modifier.width(300.dp),
                    value = recipePortions.value,
                    onValueChange = {
                        recipePortions.value = it
                        if (recipePortions.value != "") {
                            rvm.selectedRecipe.portions = recipePortions.value.toInt()
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    label = { Text("Portions") })
                TextField(
                    modifier = Modifier.width(300.dp),
                    value = recipeUrl.value,
                    onValueChange = {
                        recipeUrl.value = it
                        if (recipeUrl.value != "") {
                            rvm.selectedRecipe.url = recipeUrl.value
                        }
                    },
                    singleLine = true,
                    label = { Text("Image Url") })
                TextField(
                    modifier = Modifier.width(300.dp),
                    value = recipeDescription.value,
                    onValueChange = {
                        recipeDescription.value = it
                        rvm.selectedRecipe.description = it
                    },
                    minLines = 10,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    label = { Text("Description") }
                )
                if (rvm.selectedRecipe.products.isNotEmpty()) {
                    rvm.selectedRecipe.products.forEach { p ->
                        val name = p.split(":")[1]
                        val weight = p.split(":")[0]
                        ingredientsList.add(Pair(name, weight))
                    }
                }
                for ((index, ingredient) in ingredientsList.withIndex()) {
                    IngredientInput(
                        ingredient.second,
                        ingredient.first,
                        { newWeight -> updateWeight(index, newWeight) },
                        { newName -> updateName(index, newName) })
                }
                CustomButton(text = "Add Ingredient", onClick = {
                    ingredientsList.add(Pair("", ""))
                }, modifier = Modifier)
                CustomButton(text = "Save", onClick = {
                    val recipeIngredients = getAllIngredientsAsStrings()
                    rvm.selectedRecipe.products = recipeIngredients
                    rvm.addRecipe()
                    navController.navigateUp()
                }, modifier = Modifier)
            }
        }

        CustomButton(
            Modifier
                .align(Alignment.BottomEnd)
                .offset((-10).dp, (-75).dp), painterResource(id = R.drawable.close)
        ) {
            navController.navigateUp()
        }

    }
}


@Composable
private fun IngredientInput(
    ingredientName: String, ingredientWeight: String, onProductWeightChange: (String) -> Unit,
    onProductNameChange: (String) -> Unit,
) {

    var productName by remember { mutableStateOf(ingredientName) }
    var productWeight by remember { mutableStateOf(ingredientWeight) }
    Row(
        Modifier.width(300.dp)
    ) {
        TextField(
            modifier = Modifier.width(100.dp),
            value = productWeight,
            onValueChange = {
                productWeight = it
                onProductWeightChange(productWeight)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Weight") }
        )
        TextField(
            modifier = Modifier.width(200.dp),
            value = productName,
            onValueChange = {
                productName = it
                onProductNameChange(productName)
            },
            singleLine = true,
            label = { Text("Ingredient") }
        )
    }
}


@Preview(name = "SecretRecipeScreen")
@Composable
private fun PreviewSecretRecipeScreen() {
    SecretRecipeScreen(rememberNavController())
}
