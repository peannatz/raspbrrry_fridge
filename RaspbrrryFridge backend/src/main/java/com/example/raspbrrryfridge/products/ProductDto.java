package com.example.raspbrrryfridge.products;

import java.time.LocalDateTime;

public record ProductDto(String name, int weight, LocalDateTime mhd) {
}
