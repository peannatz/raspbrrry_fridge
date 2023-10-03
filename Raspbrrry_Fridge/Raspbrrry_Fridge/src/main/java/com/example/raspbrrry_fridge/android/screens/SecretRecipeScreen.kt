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
    var dropdownExpanded = remember { mutableStateOf(true) }
    rvm.getAllRecipes()
    val recipes = rvm.recipes.collectAsState()

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
                    modifier = if (recipes.value.isNotEmpty()) {
                        Modifier
                            .clickable(onClick = { dropdownExpanded.value = true })
                            .border(width = 2.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            .width(300.dp)
                            .height(50.dp)
                    } else {
                        Modifier
                    }
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
                    value = recipeDescription.value,
                    onValueChange = {
                        recipeDescription.value = it
                        rvm.selectedRecipe.description = it
                    },
                    minLines = 10,
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    label = { Text("Description") }
                )
                CustomButton(text = "Save", onClick = {
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

@Preview(name = "SecretRecipeScreen")
@Composable
private fun PreviewSecretRecipeScreen() {
    SecretRecipeScreen(rememberNavController())
}
