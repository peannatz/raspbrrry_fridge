package com.example.raspbrrry_fridge.android.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.raspbrrry_fridge.android.R
import com.example.raspbrrry_fridge.android.components.CustomButton
import com.example.raspbrrry_fridge.android.data.Product
import com.example.raspbrrry_fridge.android.viewModel.BarcodeScannerViewModel
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun ProduceInputPopup(
    navController: NavController,
    visible: MutableState<Boolean>
) {

    if (visible.value) {

        val barcodeScannerViewModel: BarcodeScannerViewModel = viewModel(LocalContext.current as ComponentActivity)
        val currentDate = LocalDate.now()
        val typography = MaterialTheme.typography

        var weight by remember { mutableStateOf("100") }
        var year by remember { mutableIntStateOf(currentDate.year) }
        var month by remember { mutableIntStateOf(currentDate.monthValue) }
        var day by remember { mutableIntStateOf(currentDate.dayOfMonth) }
        var fillSwitch by remember { mutableStateOf(true) }
        val product = barcodeScannerViewModel.scannedRawProduct
        val eanProducts = barcodeScannerViewModel.eanProducts.collectAsState()

        fun addItemToFridge() {
            val productInput = Product(
                name = product.product_name,
                weight = weight.toInt(),
                mhd = LocalDate.of(year, month, day).toString(),
                ean = product._id,
                url = product.image_front_url,
                categories_tag = product.category,
                tag = product.category
            )
            barcodeScannerViewModel.addProduct(productInput)
            navController.navigateUp()
        }

        fun editItem() {
            if (barcodeScannerViewModel.selectedProduct.value.weight == weight.toInt()) {
                barcodeScannerViewModel.removeProduct()
                barcodeScannerViewModel.updateProduct(product._id)
            } else if (barcodeScannerViewModel.selectedProduct.value.weight > weight.toInt()) {
                barcodeScannerViewModel.editProduct(weight.toInt())
                barcodeScannerViewModel.updateProduct(product._id)
                return
            } else {
                //TODO
            }
            navController.navigateUp()
        }

        fun removeItem() {
            barcodeScannerViewModel.removeAllProductsWithEan()
        }

        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(10.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = product.brands, style = typography.bodyMedium)
                Text(text = product.product_name_de, style = typography.titleMedium)
                Spacer(Modifier.height(20.dp))
                TextField(
                    value = weight,
                    onValueChange = {
                        weight=it
                    },
                    label = { Text("Weight in g") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                if (fillSwitch) {
                    Spacer(Modifier.height(30.dp))
                    Text(text = "Best Before", style = typography.bodyMedium)
                    Row {
                        NumberPicker(
                            value = day,
                            onValueChange = { day = it },
                            range = 1.rangeTo(YearMonth.of(year, month).lengthOfMonth()),
                            dividersColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        NumberPicker(
                            value = month,
                            onValueChange = { month = it },
                            range = 1.rangeTo(12),
                            dividersColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        NumberPicker(
                            value = year,
                            onValueChange = { year = it },
                            range = currentDate.year.rangeTo(currentDate.year + 100),
                            dividersColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                Spacer(Modifier.height(30.dp))
                Row {

                    Button(
                        onClick = {
                            if (fillSwitch) {
                                barcodeScannerViewModel.selectedIndex.intValue = -1
                                addItemToFridge()
                            } else {
                                editItem()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
                        enabled = fillSwitch || barcodeScannerViewModel.selectedIndex.intValue > -1
                    ) {
                        Text(
                            if (fillSwitch) {
                                "Add"
                            } else {
                                "Remove"
                            },
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }

                    if (!fillSwitch) {
                        Spacer(modifier = Modifier.width(20.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
                            enabled = barcodeScannerViewModel.selectedIndex.intValue > -1,
                            onClick = { removeItem() }
                        ) {
                            Text(
                                "Remove All", color = MaterialTheme.colorScheme.primaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    if (eanProducts.value.isNotEmpty()) {
                        Switch(checked = fillSwitch, onCheckedChange = {
                            fillSwitch = it
                            if (fillSwitch) barcodeScannerViewModel.selectedIndex.intValue > -1
                        })
                    }
                }
                if (eanProducts.value.isNotEmpty()) {
                    Spacer(Modifier.height(30.dp))
                    Text("Already in your Fridge:", style = typography.bodyMedium)
                    LazyColumn(
                        Modifier.fillMaxHeight(0.2f)
                    ) {
                        items(eanProducts.value) { item ->
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.Top,
                                modifier =
                                if (!fillSwitch) {
                                    Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                            selected = item.id == barcodeScannerViewModel.selectedIndex.intValue,
                                            onClick = {
                                                if (barcodeScannerViewModel.selectedIndex.intValue != item.id) {
                                                    barcodeScannerViewModel.selectedIndex.intValue =
                                                        item.id
                                                    barcodeScannerViewModel.getProductForId()
                                                } else {
                                                    barcodeScannerViewModel.selectedIndex.intValue = -1
                                                }
                                            })
                                        .background(if (barcodeScannerViewModel.selectedIndex.intValue == item.id) MaterialTheme.colorScheme.primary else Color.Transparent)
                                } else {
                                    Modifier.fillMaxWidth()
                                }
                            ) {
                                Text(
                                    text = "${item.weight}g",
                                    style = if (barcodeScannerViewModel.selectedIndex.intValue == item.id) typography.labelSmall else typography.labelMedium,
                                )
                                Text(
                                    text = item.mhd,
                                    style = if (barcodeScannerViewModel.selectedIndex.intValue == item.id) typography.labelSmall else typography.labelMedium,
                                )
                            }
                        }
                    }
                }
                if (!fillSwitch && barcodeScannerViewModel.selectedIndex.intValue == -1) {
                    Text("Please select a batch to remove")
                }
            }
            CustomButton(
                Modifier
                    .align(Alignment.BottomEnd)
                    .offset((-10).dp, (-75).dp), painterResource(id = R.drawable.close)
            ) {
                barcodeScannerViewModel.showInputPopup.value = false
                navController.navigateUp()
            }
        }
    }
}

@Preview(name = "ProduceInputPopup")
@Composable
private fun PreviewProduceInputPopup() {
    //ProduceInputPopup(rememberNavController())
}
