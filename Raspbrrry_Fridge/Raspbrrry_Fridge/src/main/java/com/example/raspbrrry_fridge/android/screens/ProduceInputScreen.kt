package com.example.raspbrrry_fridge.android.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.raspbrrry_fridge.android.R
import com.example.raspbrrry_fridge.android.components.CustomButton
import com.example.raspbrrry_fridge.android.data.Product
import com.example.raspbrrry_fridge.android.ui.theme.MyCoolTheme
import com.example.raspbrrry_fridge.android.viewModel.BarcodeScannerViewModel
import com.example.raspbrrry_fridge.android.viewModel.ProductViewModel
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

        var weight by remember { mutableIntStateOf(100) }
        var year by remember { mutableIntStateOf(currentDate.year) }
        var month by remember { mutableIntStateOf(currentDate.monthValue) }
        var day by remember { mutableIntStateOf(currentDate.dayOfMonth) }
        var fillSwitch by remember { mutableStateOf(true) }
        val product = barcodeScannerViewModel.scannedRawProduct

        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inversePrimary)
        ) {
            Box(
                Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.6f)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .alpha(1f)
                    .align(Alignment.Center)
            ) {
                CustomButton(
                    Modifier
                        .align(Alignment.TopEnd)
                        .offset((-5).dp), painterResource(id = R.drawable.close)
                ) {
                    barcodeScannerViewModel.showInputPopup.value = false
                    navController.navigateUp()
                }
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = product.brands, style = typography.bodyMedium)
                    Text(text = product.product_name_de, style = typography.titleMedium)
                    Spacer(Modifier.height(20.dp))
                    TextField(
                        value = weight.toString(),
                        onValueChange = { weight = it.toInt() },
                        label = { Text("Weight in g") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
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
                    Spacer(Modifier.height(30.dp))
                    Row {
                        Button(
                            onClick = {
                                val productInput = Product(
                                    name = product.product_name,
                                    weight = weight.toString(),
                                    mhd = LocalDate.of(year, month, day).toString()
                                )
                                barcodeScannerViewModel.addProduct(productInput)
                                navController.navigateUp()
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.primary),
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
                        Spacer(modifier = Modifier.width(20.dp))
                        Switch(checked = fillSwitch, onCheckedChange = { fillSwitch = it })
                    }
                }
            }
        }
    }
}

@Preview(name = "ProduceInputPopup")
@Composable
private fun PreviewProduceInputPopup() {
    //ProduceInputPopup(rememberNavController())
}
