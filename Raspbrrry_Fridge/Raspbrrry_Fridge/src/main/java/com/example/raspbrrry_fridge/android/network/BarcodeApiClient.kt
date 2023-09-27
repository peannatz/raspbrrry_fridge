package com.example.raspbrrry_fridge.android.network

object BarcodeApiClient : NetworkClient() {

    fun getProductByEan(ean: String): String {
        val url = "$apiUrl$ean.json"
        return getRequest(url)
    }

    val apiUrl = "https://world.openfoodfacts.org/api/v2/product/"
}
