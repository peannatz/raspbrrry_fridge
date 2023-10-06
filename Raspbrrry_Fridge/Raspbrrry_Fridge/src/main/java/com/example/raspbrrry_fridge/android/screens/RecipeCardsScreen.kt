package com.example.raspbrrry_fridge.android.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.alexstyl.swipeablecard.Direction
import com.alexstyl.swipeablecard.ExperimentalSwipeableCardApi
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.alexstyl.swipeablecard.swipableCard
import com.example.raspbrrry_fridge.android.R
import com.example.raspbrrry_fridge.android.components.CustomButton
import com.example.raspbrrry_fridge.android.viewModel.RecipeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalSwipeableCardApi::class)
@Composable
fun RecipeCardsScreen(
    navController: NavController
) {
    val loginBackStackEntry = remember { navController.previousBackStackEntry }

    val recipeViewModel: RecipeViewModel = if (loginBackStackEntry != null) {
        viewModel(loginBackStackEntry)
    } else {
        viewModel()
    }

    recipeViewModel.getAllRecipes()
    val recipes by recipeViewModel.recipes.collectAsState()
    var recipeList = remember { mutableStateOf(recipes) }
    val scope = rememberCoroutineScope()
    var randomizedRecipes by remember { mutableStateOf(false) }
    val lastRoute = navController.previousBackStackEntry?.destination?.route

    val isLoading = recipes.isEmpty() && !recipeViewModel.isLoadingRecipes

    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.primary
        )
        return
    }

    if (lastRoute == "RecipesScreen" && !randomizedRecipes) {
        val pressedRecipe = recipes.find { r -> r.id == recipeViewModel.selectedRecipeId }

        if (pressedRecipe != null) {
            val filteredRecipes = recipes.filterNot { it.id == recipeViewModel.selectedRecipeId }
            val randomRecipes = filteredRecipes.shuffled()
            recipeViewModel.updateRecipeOrder(randomRecipes + listOf(pressedRecipe))
            recipeList.value = randomRecipes + listOf(pressedRecipe)
        } else {
            val randomRecipes = recipes.shuffled()
            recipeViewModel.updateRecipeOrder(randomRecipes)
            recipeList.value = randomRecipes
        }
        randomizedRecipes = true
    }

    val states = recipeList.value.map { it to rememberSwipeableCardState() }

    Box(
        Modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {
            states.forEach { (recipe, state) ->
                if (state.swipedDirection == null) {
                    var showBackSide by remember { mutableStateOf(false) }
                    Box(Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp)
                        .swipableCard(
                            state = state,
                            blockedDirections = listOf(Direction.Down),
                            onSwiped = {
                                // swipes are handled by the LaunchedEffect
                                // so that we track button clicks & swipes
                                // from the same place
                            },
                            onSwipeCancel = {
                                Log.d("Swipeable-Card", "Cancelled swipe")
                            }
                        )) {
                        Column(
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.inversePrimary)
                        ) {
                            Text(
                                recipe.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 30.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp)
                            )
                            if (showBackSide) {
                                Box(
                                    Modifier
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .fillMaxSize()
                                        .padding(24.dp)
                                        .clickable { showBackSide = !showBackSide }
                                        .verticalScroll(rememberScrollState())) {
                                    Column(Modifier.fillMaxSize()) {
                                        Text("For ${recipe.portions} portions you need:")
                                        Spacer(Modifier.height(20.dp))
                                        if (recipe.ingredients.isNotEmpty()) {
                                            recipe.ingredients.forEach {
                                                Row {
                                                    Text("${it.value}g ")
                                                    Text(it.key)
                                                }
                                            }
                                        }
                                        Spacer(Modifier.height(20.dp))
                                        Text(recipe.description)
                                    }
                                }
                            } else {
                                AsyncImage(
                                    modifier = Modifier.clickable { showBackSide = !showBackSide },
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(recipe.url)
                                        .build(), contentDescription = "${recipe.name} Image",
                                    contentScale = ContentScale.FillHeight
                                )
                            }
                        }
                    }
                    LaunchedEffect(recipe, state.swipedDirection) {
                        if (state.swipedDirection != null) {
                            println("swiped ${recipe.name}")
                        }
                    }
                }
            }
            Row(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            val last = states.reversed()
                                .firstOrNull {
                                    it.second.offset.value == Offset(0f, 0f)
                                }?.second
                            last?.swipe(Direction.Left)
                        }
                    }) {
                    Icon(painterResource(R.drawable.dislike), contentDescription = null)
                }
                Button(
                    onClick = {
                        scope.launch {
                            val last = states.reversed()
                                .firstOrNull {
                                    it.second.offset.value == Offset(0f, 0f)
                                }?.second
                            last?.swipe(Direction.Down)
                        }
                    },
                ) {
                    Icon(painterResource(R.drawable.bookmark), contentDescription = null)
                }
                Button(
                    onClick = {
                        scope.launch {
                            val last = states.reversed()
                                .firstOrNull {
                                    it.second.offset.value == Offset(0f, 0f)
                                }?.second

                            last?.swipe(Direction.Right)
                        }
                    }
                ) {
                    Icon(painterResource(R.drawable.favourite), contentDescription = null)
                }
            }
        }
        CustomButton(
            Modifier
                .align(Alignment.BottomEnd)
                .offset((-10).dp, (-120).dp),
            painterResource(id = R.drawable.close)
        ) {
            navController.navigateUp()
        }
    }
}
