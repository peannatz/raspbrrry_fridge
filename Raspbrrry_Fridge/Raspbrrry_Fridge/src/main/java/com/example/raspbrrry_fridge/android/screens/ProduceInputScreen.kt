package com.example.raspbrrry_fridge.android.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.raspbrrry_fridge.android.data.Product
import com.example.raspbrrry_fridge.android.viewModel.BarcodeScannerViewModel
import com.example.raspbrrry_fridge.android.viewModel.ProductViewModel
import java.sql.Date
import java.text.DateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

@Composable
fun ProduceInputScreen(
    navController: NavController
) {
    val barcodeScannerViewModel: BarcodeScannerViewModel = viewModel(LocalContext.current as ComponentActivity)
    val productViewModel: ProductViewModel = viewModel()
    val currentDate = LocalDate.now()

    var weight by remember { mutableIntStateOf(100) }
    var year by remember { mutableIntStateOf(currentDate.year) }
    var month by remember { mutableIntStateOf(currentDate.monthValue) }
    var day by remember { mutableIntStateOf(currentDate.dayOfMonth) }
    val product = barcodeScannerViewModel.scannedRawProduct


    Box {
        Button(
            onClick = { navController.navigateUp() },
            content = { Icon(Icons.Default.Close, contentDescription = "") },
            modifier = Modifier.align(Alignment.TopEnd),
            shape = RoundedCornerShape(15.dp)
        )
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(text = product.brands)
            Text(text = product.product_name_de)
            TextField(
                value = weight.toString(),
                onValueChange = { weight = it.toInt() },
                label = { Text("Weight in g") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Row {
                NumberPicker(
                    value = day,
                    onValueChange = { day = it },
                    range = 1.rangeTo(YearMonth.of(year, month).lengthOfMonth())
                )
                NumberPicker(
                    value = month,
                    onValueChange = { month = it },
                    range = 1.rangeTo(12)
                )
                NumberPicker(
                    value = year,
                    onValueChange = { year = it },
                    range = year.rangeTo(year + 100)
                )
            }
            Button(onClick = {
                val productInput = Product(
                    name = product.product_name,
                    weight = weight.toString(),
                    mhd = LocalDate.of(year, month, day)
                )
                productViewModel.addProduct(productInput)
            }) {
                Text("Confirm")
            }
        }
    }
}

@Preview(name = "ProduceInputScreen")
@Composable
private fun PreviewProduceInputScreen() {
    //ProduceInputScreen()
}
