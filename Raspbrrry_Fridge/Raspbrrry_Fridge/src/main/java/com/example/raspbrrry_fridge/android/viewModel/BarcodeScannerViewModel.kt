package com.example.raspbrrry_fridge.android.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raspbrrry_fridge.android.data.Product
import com.example.raspbrrry_fridge.android.data.RawProduct
import com.example.raspbrrry_fridge.android.data.ProductResponse
import com.example.raspbrrry_fridge.android.network.BarcodeApiClient.getProductByEan
import com.example.raspbrrry_fridge.android.network.ProductClient
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BarcodeScannerViewModel : ViewModel() {

    val eanProductsList = MutableStateFlow(listOf<Product>())
    val eanProducts: StateFlow<List<Product>> get() = eanProductsList

    lateinit var scannedRawProduct: RawProduct
    var selectedIndex = mutableIntStateOf(-1)
    var selectedProduct = MutableStateFlow(Product())

    var showInputPopup = mutableStateOf(false)

    private suspend fun fetchProductData(result: String): RawProduct = withContext(Dispatchers.IO) {
        val apiResponse = JsonParser.parseString(getProductByEan(result)).asJsonObject
        val gson = Gson()

        return@withContext try {
            val myData = gson.fromJson(apiResponse, ProductResponse::class.java)
            myData.product
        } catch (e: Exception) {
            e.printStackTrace()
            RawProduct() // Return an empty Product for demonstration purposes
        }
    }

    fun getProductForId() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fetchedProduct = ProductClient.getById(selectedIndex.intValue)
                selectedProduct.emit(fetchedProduct)
            }

        }
    }

    private suspend fun getAllProductsWithEan() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fetchedProducts = ProductClient.getByEan(scannedRawProduct._id)
                eanProductsList.emit(fetchedProducts)
            }
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

    fun updateProduct(ean: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                onBarcodeScanned(ean)
            }
        }
    }

    suspend fun onBarcodeScanned(ean: String) {
        scannedRawProduct = fetchProductData(ean)
        getAllProductsWithEan()
        showInputPopup.value = true
        selectedIndex.intValue = -1
        selectedProduct.value = Product()
    }

    fun editProduct(weightDiff: Int) {
        val newWeight = selectedProduct.value.weight - weightDiff
        val newProduct = Product(
            selectedProduct.value.id,
            selectedProduct.value.name,
            newWeight,
            selectedProduct.value.mhd,
            selectedProduct.value.ean,
            selectedProduct.value.url,
            selectedProduct.value.categories_tag,
            selectedProduct.value.tag
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ProductClient.editProduct(selectedProduct.value.id, newProduct)
            }
        }
    }

    fun removeProduct() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ProductClient.deleteProduct(selectedProduct.value.id)
            }
        }
    }

    fun removeAllProductsWithEan(){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                eanProducts.value.forEach {ProductClient.deleteProduct(it.id) }
            }
        }
    }
}
