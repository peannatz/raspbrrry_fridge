package com.example.raspbrrry_fridge.android.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.raspbrrry_fridge.android.R
import com.example.raspbrrry_fridge.android.components.GeneralScaffold
import com.example.raspbrrry_fridge.android.components.PermissionRequestComponent
import com.example.raspbrrry_fridge.android.viewModel.BarcodeScannerViewModel
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.*

@Composable
fun ScanProduceScreen(
    navController: NavController,
    barcodeScannerViewModel: BarcodeScannerViewModel = viewModel()
) {
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
                    barcodeScannerViewModel.onBarcodeScanned(result.text)
                    torchOn = false
                    setTorchOff()
                    navController.navigateUp()
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
    ) {
        AndroidView(
            factory = { barcodeView },
            modifier = Modifier.fillMaxSize()
        ) {

            // Start scanning if permission is granted
            if (context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                startScanning()
            } else {
                // Request camera permission
                // cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
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
