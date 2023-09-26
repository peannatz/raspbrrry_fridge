package com.example.raspbrrry_fridge

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
