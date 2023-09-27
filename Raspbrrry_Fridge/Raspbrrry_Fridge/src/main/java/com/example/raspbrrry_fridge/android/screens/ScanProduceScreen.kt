package com.example.raspbrrry_fridge.android.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.raspbrrry_fridge.android.R
import com.example.raspbrrry_fridge.android.components.GeneralScaffold
import com.example.raspbrrry_fridge.android.components.PermissionRequestComponent
import com.example.raspbrrry_fridge.android.viewModel.BarcodeScannerViewModel
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.*
import kotlinx.coroutines.launch

@Composable
fun ScanProduceScreen(
    navController: NavController,
) {
    val currentlyLoading = remember {
        mutableStateOf(false)
    }
    OverlayLoadingScreen(
        isLoading = currentlyLoading
    ) // Set this to true when waiting for the API call to complete
    {
        // Your existing content goes here
        // This is where you can place your main UI
        val barcodeScannerViewModel: BarcodeScannerViewModel = viewModel(LocalContext.current as ComponentActivity)

        GeneralScaffold(navController = navController) {

        }
        var isPermissionGranted by remember { mutableStateOf(false) }

        if (!isPermissionGranted) {
            PermissionRequestComponent { granted ->
                isPermissionGranted = granted
                if (!granted) {
                    print("notGranted")
                    // Handle the case where permission is not granted
                    // You can show a message or take appropriate action
                    // e.g., show a message using a Snackbar
                }
            }
        }

        var torchOn by remember { mutableStateOf(false) }

        val context = LocalContext.current

        val barcodeView = remember {
            DecoratedBarcodeView(context).apply {
                decodeSingle(object : BarcodeCallback {
                    override fun barcodeResult(result: BarcodeResult) {
                        // Handle the scanned result here
                        torchOn = false
                        setTorchOff()
                        currentlyLoading.value = true
                        barcodeScannerViewModel.viewModelScope.launch {
                            barcodeScannerViewModel.onBarcodeScanned(result.text, navController)
                            currentlyLoading.value = false
                            navController.navigate("ProduceInputScreen")
                        }
                    }

                    override fun possibleResultPoints(resultPoints: List<ResultPoint>) {
                        // Handle possible result points here
                    }
                })
            }
        }

        fun startScanning() {
            barcodeView.resume()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer),
            ) {
                AndroidView(
                    factory = { barcodeView },
                    modifier = Modifier.fillMaxSize(),
                ) {
                    startScanning()
                }
//            } else {
//                barcodeView.pause()
//                Text(text = "No internet connection :(")
//            }
            //TODO: add overlay when no internet connection available and store scanned info somewhere
            //TODO: Feedback on 404

            Button(
                onClick = { navController.navigateUp() },
                content = { Icon(Icons.Default.Close, contentDescription = "") },
                modifier = Modifier.align(Alignment.TopEnd),
                shape = RoundedCornerShape(15.dp)
            )
            Button(
                onClick = {
                    torchOn = !torchOn
                    if (torchOn) {
                        barcodeView.setTorchOn()
                    } else {
                        barcodeView.setTorchOff()
                    }
                },
                modifier = Modifier.align(Alignment.BottomEnd),
                shape = RoundedCornerShape(15.dp),
                content = {
                    Icon(
                        painter = painterResource(
                            id =
                            if (torchOn) {
                                R.drawable.light_off
                            } else {
                                R.drawable.light_on
                            }
                        ), contentDescription = null
                    )
                }

            )
        }
    }
}

@Composable
fun OverlayLoadingScreen(
    isLoading: MutableState<Boolean>,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        content() // Display the content (your main UI)

        if (isLoading.value) {
            // Display an overlay with a semi-transparent background
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                // You can add loading indicators or messages here
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}

