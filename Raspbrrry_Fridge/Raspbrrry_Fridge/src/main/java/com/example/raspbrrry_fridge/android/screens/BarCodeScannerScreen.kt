package com.example.raspbrrry_fridge.android.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.raspbrrry_fridge.android.viewModel.BarcodeScannerViewModel
import com.journeyapps.barcodescanner.*

@Composable
fun BarcodeScannerScreen(barcodeScannerViewModel: BarcodeScannerViewModel = viewModel()) {
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result ->
        run {
           barcodeScannerViewModel.onBarcodeScanned(result.contents)
        }
    }

    Button(onClick = { scanLauncher.launch(ScanOptions()) }) {
        Text(text = "Scan barcode")
    }
}
