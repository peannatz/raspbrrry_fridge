package com.example.raspbrrry_fridge.android.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.raspbrrry_fridge.android.data.Product
import com.example.raspbrrry_fridge.android.data.RawProduct
import com.example.raspbrrry_fridge.android.data.ProductResponse
import com.example.raspbrrry_fridge.android.network.BarcodeApiClient.getProductByEan
import com.example.raspbrrry_fridge.android.network.ProductClient
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BarcodeScannerViewModel : ViewModel() {

    lateinit var scannedRawProduct: RawProduct
    var showInputPopup = mutableStateOf(false)

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

    fun addProduct(product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ProductClient.addProduct(product)
            }
        }
        showInputPopup.value = false
    }

    suspend fun onBarcodeScanned(ean: String, navController: NavController) {
        scannedRawProduct = fetchProduct(ean)
        showInputPopup.value = true
    }
}
