package com.example.raspbrrry_fridge.android.network

import com.example.raspbrrry_fridge.android.data.Product
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object ProductClient : NetworkClient() {

    val productEndpoint = "$backendUrl/products"
    private val productListType: Type = object : TypeToken<List<Product>>() {}.type
    private val productType: Type = object : TypeToken<Product>() {}.type

    fun getAllProducts(): List<Product> {
        val response = getRequest("$productEndpoint/findAll")
        return gson.fromJson(response, productListType)
    }

    fun getByEan(ean: String): List<Product> {
        val response = getRequest("$productEndpoint/findByEan/$ean")
        if (response == "") {
            return listOf()
        }
        return gson.fromJson(response, productListType)
    }

    fun getById(id: Int): Product {
        val response = getRequest("$productEndpoint/find/$id")
        return gson.fromJson(response, productType)
    }

    fun addProduct(product: Product) {
        val json = gson.toJson(product, productType)
        postRequest("$productEndpoint/add", json)
    }

    fun editProduct(id: Int, product: Product) {
        val json = gson.toJson(product, productType)
        postRequest("$productEndpoint/edit/$id", json)
    }

    fun deleteProduct(id: Int) {
        postRequest("$productEndpoint/delete/$id", "")
    }
}
