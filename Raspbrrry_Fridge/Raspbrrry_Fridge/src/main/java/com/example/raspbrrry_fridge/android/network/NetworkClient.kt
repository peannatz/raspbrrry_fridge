import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

fun makeApiRequest(ean: String): String {
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(apiUrl + ean +".json")
        .build()

    try {
        val response: Response = client.newCall(request).execute()
        if (response.isSuccessful) {
            return response.body?.string() ?: ""
        } else {
            // Handle error, e.g., response.code(), response.message()
            throw IOException("HTTP Error: ${response.code}")
        }
    } catch (e: IOException) {
        e.printStackTrace()
        // Handle exceptions
        return ""
    }
}

// Example usage
val apiUrl = "https://world.openfoodfacts.org/api/v2/product/"


