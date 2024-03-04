package com.example.raspbrrry_fridge.android.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.raspbrrry_fridge.android.components.GeneralScaffold
import com.example.raspbrrry_fridge.android.viewModel.ProductViewModel
import com.example.raspbrrry_fridge.android.components.ProduceItems

@Composable
fun ProduceScreen(
    navController: NavController,
    pvm: ProductViewModel = viewModel()
) {
    pvm.fetchProducts()

    GeneralScaffold(navController) {
        ProduceItems(pvm)
    }
    if(pvm.showDetailView){
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))
                .fillMaxSize()
        ) {
            Box(
                Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.6f)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .align(Alignment.Center)) {
                Button(
                    onClick = { pvm.showDetailView= !pvm.showDetailView },
                    content = { Icon(Icons.Default.Close, contentDescription = "") },
                    modifier = Modifier.align(Alignment.TopEnd),
                    shape = CircleShape
                )
                Text(pvm.selectedProduct.name, Modifier.align(Alignment.TopCenter))
                Text(pvm.selectedProduct.weight.toString(), Modifier.align(Alignment.CenterStart))
                Text(pvm.selectedProduct.mhd, Modifier.align(Alignment.CenterEnd))
            }
        }
    }
}

@Preview(name = "ProduceScreen")
@Composable
private fun PreviewProduceScreen() {
    ProduceScreen(rememberNavController())
}
