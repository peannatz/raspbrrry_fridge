package com.example.raspbrrry_fridge.android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.raspbrrry_fridge.android.data.Product
import com.example.raspbrrry_fridge.android.viewModel.ProductViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun ProduceItems(pvm: ProductViewModel) {

    val products: List<Product> by pvm.products.collectAsState()
    val typography = MaterialTheme.typography
    var searchInput by remember { mutableStateOf("") }

    Column(Modifier.padding(bottom = 80.dp)) {

        TextField(value = searchInput,
            onValueChange = { searchInput = it }, Modifier.fillMaxWidth(), label = { Text(text = "Search")}, singleLine = true)

        Row(Modifier.background(MaterialTheme.colorScheme.onPrimary)) {
            Spacer(Modifier.width(125.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(text = "Product", Modifier.align(Alignment.TopStart), style = typography.titleMedium)
                Text(text = "Weight", Modifier.align(Alignment.CenterStart), style = typography.labelMedium)
                Text(text = "Best Before", Modifier.align(Alignment.CenterEnd), style = typography.labelMedium)
                Text(text = "EcoScore", Modifier.align(Alignment.BottomStart), style = typography.labelMedium)
                Text(text = "NutriScore", Modifier.align(Alignment.BottomCenter), style = typography.labelMedium)
                Text(text = "NovaGroup", Modifier.align(Alignment.BottomEnd), style = typography.labelMedium)
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
                        .clickable { pvm.selectProduct(item) },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
//                    AsyncImage(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            //.data(item.image_front_url)
//                            .build(), contentDescription = "${item.name} Image", Modifier.height(100.dp)
//                    )

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(text = item.name, Modifier.align(Alignment.TopStart), style = typography.titleMedium)
                        Text(
                            text = item.weight,
                            Modifier.align(Alignment.CenterStart),
                            style = typography.bodyMedium
                        )
                        Text(
                            text = item.mhd,
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
