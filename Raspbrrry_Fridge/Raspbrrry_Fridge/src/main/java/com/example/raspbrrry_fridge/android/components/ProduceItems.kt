package com.example.raspbrrry_fridge.android.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.raspbrrry_fridge.android.data.Product
import com.example.raspbrrry_fridge.android.viewModel.ProductViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ProduceItems(pvm: ProductViewModel) {

    val headerStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.2.em,
        color = MaterialTheme.colorScheme.primary
    )

    val infoStyle = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.W100,
        fontFamily = FontFamily.Monospace,
        letterSpacing = 0.1.em,
        color = MaterialTheme.colorScheme.secondary
    )

    val normalStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily.Monospace,
        letterSpacing = 0.2.em,
        color = MaterialTheme.colorScheme.secondary
    )

    val products: List<Product> by pvm.products.observeAsState(emptyList())

    Column(Modifier.padding(bottom = 80.dp)) {
        Row(Modifier.background(MaterialTheme.colorScheme.onPrimary)) {
            Spacer(Modifier.width(125.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(text = "Product", Modifier.align(Alignment.TopStart), style = headerStyle)
                Text(text = "Weight", Modifier.align(Alignment.CenterStart), style = infoStyle)
                Text(text = "Best Before", Modifier.align(Alignment.CenterEnd), style = infoStyle)
                Text(text = "EcoScore", Modifier.align(Alignment.BottomStart), style = infoStyle)
                Text(text = "NutriScore", Modifier.align(Alignment.BottomCenter), style = infoStyle)
                Text(text = "NovaGroup", Modifier.align(Alignment.BottomEnd), style = infoStyle)
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
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            //.data(item.image_front_url)
                            .build(), contentDescription = "${item.name} Image", Modifier.height(100.dp)
                    )

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(text = item.name, Modifier.align(Alignment.TopStart), style = headerStyle)
                        Text(text = item.weight.toString(), Modifier.align(Alignment.CenterStart), style = normalStyle)
                        Text(text = item.mhd.toString(), Modifier.align(Alignment.CenterEnd), style = normalStyle)
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
    pvm.fetchProducts(LocalContext.current)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ProduceItems(pvm)
    }
}
