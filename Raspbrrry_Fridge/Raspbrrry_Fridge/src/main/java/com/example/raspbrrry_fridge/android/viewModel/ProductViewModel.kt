package com.example.raspbrrry_fridge.android.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raspbrrry_fridge.android.data.Product
import com.example.raspbrrry_fridge.android.data.RawProduct
import com.example.raspbrrry_fridge.android.network.ProductClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel : ViewModel() {
    val productsList = MutableStateFlow(listOf<Product>())
    val products: StateFlow<List<Product>> get() = productsList

    lateinit var selectedProduct: Product
    var showDetailView by mutableStateOf(false)

    fun selectProduct(product: Product) {
        selectedProduct = product
        showDetailView = !showDetailView
    }

    fun fetchProducts() {
        // Use the repository to fetch products from the backend
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fetchedProducts = ProductClient.getAllProducts()
                productsList.emit(fetchedProducts)

            }
        }
    }
}
