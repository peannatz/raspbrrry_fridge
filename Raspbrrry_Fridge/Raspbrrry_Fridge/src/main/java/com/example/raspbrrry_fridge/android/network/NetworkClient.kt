package com.example.raspbrrry_fridge.android.network

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

abstract class NetworkClient {

    val deviceEmulator = "http://10.0.2.2:8080"
    val usbConnectedDevice = "http://localhost:8080"
    val device = "http://192.168.2.171:8080"
    val backendUrl = usbConnectedDevice
    protected val gson = Gson()
    val client = OkHttpClient()

    fun getRequest(url: String): String {
        val request = Request.Builder()
            .url(url)
            .build()

        return try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful || response.code == 404) {
                response.body!!.string() ?: ""
            } else {
                // Handle error, e.g., response.code(), response.message()
                throw IOException("HTTP Error: ${response.code}")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle exceptions
            ""
        }
    }

    protected fun postRequest(url: String, body: String): String? {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = body.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        try {
            val response: Response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                println("Unsuccessful response: ${response.code} ${response.message}")
            } else {
                return response.body!!.string()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}
