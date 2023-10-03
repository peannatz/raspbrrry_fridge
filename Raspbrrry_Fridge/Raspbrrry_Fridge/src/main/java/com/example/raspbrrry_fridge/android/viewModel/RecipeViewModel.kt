package com.example.raspbrrry_fridge.android.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raspbrrry_fridge.android.data.Recipe
import com.example.raspbrrry_fridge.android.network.RecipeClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeViewModel : ViewModel() {

    val recipeList = MutableStateFlow(listOf<Recipe>())
    val recipes: StateFlow<List<Recipe>> get() = recipeList

    var selectedRecipeId by mutableIntStateOf(-1)
    lateinit var selectedRecipe: Recipe
    fun isSelectedRecipeInitialised() = ::selectedRecipe.isInitialized


    private var pressCount by mutableIntStateOf(0)

    fun selectRecipe(recipe: Recipe) {
        selectedRecipe = recipe
        selectedRecipeId = recipe.id
    }

    fun getAllRecipes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fetchedRecipes = RecipeClient.getAllRecipes()
                recipeList.emit(fetchedRecipes)
            }
        }
    }

    private fun getRecipe() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fetchedRecipe = RecipeClient.getRecipeById(selectedRecipeId)
                //_selectedRecipe.emit(fetchedRecipe)
            }
        }
    }

    fun addRecipe() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                RecipeClient.addRecipe(selectedRecipe)
            }
        }
    }

    fun updateProduct() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                RecipeClient.editRecipe(selectedRecipe)
            }
        }
    }

    fun deleteRecipe(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                RecipeClient.deleteRecipe(id)
            }
        }
    }

    fun handleSecretButtonPress(): Boolean {
        pressCount++
        if (pressCount == 5) {
            pressCount = 0
            return true
        } else if (pressCount == 1) {
            viewModelScope.launch(Dispatchers.IO) {
                delay(5000)
                pressCount = 0
            }
        }
        return false
    }
}
