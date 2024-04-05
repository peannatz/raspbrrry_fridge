package com.example.raspbrrry_fridge.android.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.raspbrrry_fridge.android.FridgeNotificationService
import com.example.raspbrrry_fridge.android.data.Product
import com.example.raspbrrry_fridge.android.network.ProductClient
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import java.util.concurrent.TimeUnit

class ProductViewModel : ViewModel() {

    val productsList = MutableStateFlow(listOf<Product>())
    val products: StateFlow<List<Product>> get() = productsList
    var productClient = ProductClient()

    val workManager = WorkManager.getInstance()

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
                val fetchedProducts = productClient.getAllProducts()
                productsList.emit(fetchedProducts)

            }
        }
    }

    fun scheduleDailyTask(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        val currentTime = Calendar.getInstance()
        val delayMillis = calendar.timeInMillis - currentTime.timeInMillis

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val dailyTaskRequest = PeriodicWorkRequestBuilder<FridgeNotificationService>(
            1, // Repeat interval in days
            TimeUnit.DAYS,
        )
            .setConstraints(constraints)
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "Fridge",
            ExistingPeriodicWorkPolicy.UPDATE,
            dailyTaskRequest
        )
    }
}

