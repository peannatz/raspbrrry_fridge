package com.example.raspbrrry_fridge.android.viewModel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raspbrrry_fridge.android.data.Product
import com.example.raspbrrry_fridge.android.network.ProductClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    lateinit var selectedProduct: Product
    var showDetailView by mutableStateOf(false)

    fun selectProduct(product: Product) {
        selectedProduct = product
        showDetailView = !showDetailView
    }

    fun fetchProducts(context: Context) {
        // Use the repository to fetch products from the backend
        //val fetchedProducts = ProductRepository.fetchProducts(context)
        //_products.value = fetchedProducts
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                ProductClient.addProduct(product)
            }
        }
    }
}
