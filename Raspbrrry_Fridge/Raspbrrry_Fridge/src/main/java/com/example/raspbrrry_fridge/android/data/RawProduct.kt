package com.example.raspbrrry_fridge.android.data

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class RawProduct(
    val product_name_de: String = "",
    val product_name: String = "",
    val brands: String = "",
    val image_front_url: String = "",
    val ecoscore_grade: String = "",
    val nova_group: String = "",
    val nutriscore_grade: String = "",
    val nutriments: Any = "",
    val _id: String = "",
)

data class Product(
    val id: Int = -1,
    val name: String = "",
    val weight: Int = 0,
    val mhd: String = "",
    val ean: String = "",
    val url: String = "",
){
    val formattedMhd: String
        get() {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yy");

            val localDate = LocalDate.parse(mhd, inputFormatter)
            return localDate.format(outputFormatter)
        }
    val localDateMhd: LocalDate
        get() {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return LocalDate.parse(mhd, inputFormatter)
        }
}

data class ProductResponse(
    val product: RawProduct
)
