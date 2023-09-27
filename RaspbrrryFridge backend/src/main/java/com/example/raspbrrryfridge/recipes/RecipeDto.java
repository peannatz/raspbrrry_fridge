package com.example.raspbrrryfridge.recipes;


import com.example.raspbrrryfridge.products.Product;

import java.util.List;

public record RecipeDto(String name, String description, int portions, List<Product> products) {
}
