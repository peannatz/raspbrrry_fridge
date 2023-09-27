package com.example.raspbrrry_fridge.android.viewModel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.raspbrrry_fridge.android.data.RawProduct
import com.example.raspbrrry_fridge.android.data.ProductResponse
import com.example.raspbrrry_fridge.android.network.BarcodeApiClient.getProductByEan
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BarcodeScannerViewModel : ViewModel() {

    lateinit var scannedRawProduct: RawProduct

    private suspend fun fetchProduct(result: String): RawProduct = withContext(Dispatchers.IO) {
        val apiResponse = getProductByEan(result)
        val gson = Gson()

        return@withContext try {
            val myData = gson.fromJson(apiResponse, ProductResponse::class.java)
            myData.product
        } catch (e: Exception) {
            e.printStackTrace()
            RawProduct() // Return an empty Product for demonstration purposes
        }
    }

    suspend fun onBarcodeScanned(ean: String, navController: NavController) {
        scannedRawProduct = fetchProduct(ean)
    }
}
