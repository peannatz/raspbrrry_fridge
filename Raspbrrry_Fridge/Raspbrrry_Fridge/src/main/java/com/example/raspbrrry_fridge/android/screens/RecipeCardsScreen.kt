package com.example.raspbrrry_fridge.android.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
    navController: NavController, recipeViewModel: RecipeViewModel = viewModel()
) {
    recipeViewModel.getAllRecipes()
    val recipes = recipeViewModel.recipes.collectAsState().value
    val scope = rememberCoroutineScope()

    val states = recipes.map { it to rememberSwipeableCardState() }

    Box(
        Modifier
            .padding(24.dp)
            .fillMaxSize()
    ) {
        states.forEach { (recipe, state) ->
            if (state.swipedDirection == null) {
                var showBackSide by remember { mutableStateOf(true) }
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 20.dp)
                        )

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(recipe.url)
                                .build(), contentDescription = "${recipe.name} Image",
                            contentScale = ContentScale.FillHeight
                        )

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
            CustomButton(
                onClick = {
                    scope.launch {
                        val last = states.reversed()
                            .firstOrNull {
                                it.second.offset.value == Offset(0f, 0f)
                            }?.second
                        last?.swipe(Direction.Left)
                    }
                },
                icon = painterResource(R.drawable.close)
            )
            CustomButton(
                onClick = {
                    scope.launch {
                        val last = states.reversed()
                            .firstOrNull {
                                it.second.offset.value == Offset(0f, 0f)
                            }?.second

                        last?.swipe(Direction.Right)
                    }
                },
                icon = painterResource(R.drawable.favourite)
            )
        }
    }
}
