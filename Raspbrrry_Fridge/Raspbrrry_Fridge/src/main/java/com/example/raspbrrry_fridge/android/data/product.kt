package com.example.raspbrrry_fridge.android.data

data class product(
    val product_name_de: String,
    val product_name: String,
    val brands: String,
    val image_front_url: String,
    val ecoscore_grade: String,
    val nova_group: String,
    val nutriscore_grade: String,
    val nutriments: Any,
)

data class productResponse(
    val product: product
)
