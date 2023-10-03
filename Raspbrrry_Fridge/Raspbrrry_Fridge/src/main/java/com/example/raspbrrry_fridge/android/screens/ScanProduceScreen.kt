package com.example.raspbrrry_fridge.android.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
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
import com.example.raspbrrry_fridge.android.components.CustomButton
import com.example.raspbrrry_fridge.android.components.GeneralScaffold
import com.example.raspbrrry_fridge.android.components.PermissionRequestComponent
import com.example.raspbrrry_fridge.android.viewModel.BarcodeScannerViewModel
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.*
import kotlinx.coroutines.launch
import android.Manifest

@Composable
fun ScanProduceScreen(
    navController: NavController,
) {
    val currentlyLoading = remember {
        mutableStateOf(false)
    }

    OverlayLoadingScreen(
        isLoading = currentlyLoading
    )
    {
        val barcodeScannerViewModel: BarcodeScannerViewModel = viewModel(LocalContext.current as ComponentActivity)

        GeneralScaffold(navController = navController) {

        }
        var isPermissionGranted by remember { mutableStateOf(false) }

        if (!isPermissionGranted) {
            PermissionRequestComponent(Manifest.permission.CAMERA) { granted ->
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
                        barcodeView.pause()
                        currentlyLoading.value = true
                        barcodeScannerViewModel.viewModelScope.launch {
                            barcodeScannerViewModel.onBarcodeScanned(result.text)
                            currentlyLoading.value = false
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

            val modifierClose = Modifier
                .align(Alignment.BottomEnd)
                .offset((-10).dp, (-120).dp)
            val painterClose = painterResource(id=R.drawable.close)
            val modifierTorch = Modifier
                .align(Alignment.BottomEnd)
                .offset((-10).dp, (-75).dp)
            val onClickTorch = {
                torchOn = !torchOn
                if (torchOn) {
                    barcodeView.setTorchOn()
                } else {
                    barcodeView.setTorchOff()
                }
            }
            val painterTorch = painterResource(
                id =
                if (torchOn) {
                    R.drawable.light_off
                } else {
                    R.drawable.light_on
                }
            )
            CustomButton(modifierClose, painterClose) { navController.navigateUp() }
            CustomButton(modifierTorch, painterTorch, onClickTorch)
            ProduceInputPopup(navController = navController, visible = barcodeScannerViewModel.showInputPopup)
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
        content()

        if (isLoading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }
}

