package com.example.raspbrrry_fridge.android.network

import com.example.raspbrrry_fridge.android.data.Recipe
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object RecipeClient : NetworkClient() {

    val recipeEndpoint = "$backendUrl/recipes"
    private val recipeListType: Type = object : TypeToken<List<Recipe>>() {}.type
    private val recipeType: Type = object : TypeToken<Recipe>() {}.type

    fun getAllRecipes(): List<Recipe> {
        val response = getRequest("${recipeEndpoint}/findAll")
        return gson.fromJson(response, recipeListType)
    }

    fun getRecipeById(id: Int): Recipe{
        val response = getRequest("${recipeEndpoint}/findById/$id")
        return gson.fromJson(response, recipeType)
    }

    fun addRecipe(recipe: Recipe) {
        val json = gson.toJson(recipe, recipeType)
        postRequest("${recipeEndpoint}/add", json)
    }

    fun editRecipe(recipe: Recipe) {
        val json = gson.toJson(recipe, recipeType)
        postRequest("${recipeEndpoint}/edit/${recipe.id}", json)
    }

    fun deleteRecipe(id: Int) {
        postRequest("${recipeEndpoint}/delete/$id", "")
    }
}
