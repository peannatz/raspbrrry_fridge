package com.example.raspbrrry_fridge.android.network

import android.content.Context
import com.example.raspbrrry_fridge.android.data.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import java.lang.reflect.Type

object ProductClient : NetworkClient() {

    val productEndpoint = "$backendUrl/product/"
    private val productListType: Type = object : TypeToken<List<Product>>() {}.type
    private val productType: Type = object : TypeToken<Product>() {}.type
    private val gson = Gson()
    val mediaType = "application/json; charset=utf-8".toMediaType()

    fun getAllProducts(context: Context): List<Product> {
        return listOf(Product())
    }

    fun getById(id: Int): List<Product> {
        val response = getRequest("$productEndpoint/find/$id")
        return gson.fromJson(response, productType)
    }

    fun addProduct(product: Product) {
        val json = gson.toJson(product, productType)
        postRequest("$productEndpoint/add/", json)
    }

    fun editProducts(context: Context) {
//        val gson = Gson()
//        val inputStream = context.resources.openRawResource(R.raw.sample_products)
//        val reader = JsonReader(inputStream.reader())
//        return gson.fromJson(reader, ProductRepository.productListType)
    }

    fun deleteProduct(context: Context){
//        val gson = Gson()
//        val inputStream = context.resources.openRawResource(R.raw.sample_products)
//        val reader = JsonReader(inputStream.reader())
//        return gson.fromJson(reader, ProductRepository.productListType)
    }

    val apiUrl = "https://world.openfoodfacts.org/api/v2/product/"
}
