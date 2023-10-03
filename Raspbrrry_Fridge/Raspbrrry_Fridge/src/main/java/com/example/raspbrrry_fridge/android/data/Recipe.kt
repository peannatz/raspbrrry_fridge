package com.example.raspbrrry_fridge.android.data

data class Recipe(
    val id: Int = -1,
    var name: String = "",
    var description: String = "",
    var portions: Int = 0
)
