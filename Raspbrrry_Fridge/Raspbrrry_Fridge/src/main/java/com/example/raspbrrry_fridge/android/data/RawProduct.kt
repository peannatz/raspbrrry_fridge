package com.example.raspbrrry_fridge.android.data

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
)

data class ProductResponse(
    val product: RawProduct
)
