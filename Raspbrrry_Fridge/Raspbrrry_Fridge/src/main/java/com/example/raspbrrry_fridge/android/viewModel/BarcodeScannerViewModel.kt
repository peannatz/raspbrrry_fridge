package com.example.raspbrrry_fridge.android.viewModel

import android.content.ContentValues
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raspbrrry_fridge.android.data.productResponse
import com.google.gson.Gson
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import makeApiRequest

class BarcodeScannerViewModel : ViewModel() {
    fun onBarcodeScanned(result: String) {

        Log.i(ContentValues.TAG, "scanned code: $result")
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val apiResponse = makeApiRequest(result)
                println(apiResponse)
                val gson = Gson()

                try {
                    val myData = gson.fromJson(apiResponse, productResponse::class.java)
                    println(myData.product )
                } catch (e: Exception) {
                    // Handle any parsing errors here
                }
            }
        }
    }
}
