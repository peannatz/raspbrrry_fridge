package com.example.raspbrrryfridge.domain.recipes;


import com.example.raspbrrryfridge.domain.products.Product;

import java.util.List;
import java.util.Map;

public record RecipeDto(String name, String description, int portions, List<Product> products, String url, Map<String, Double> ingredients) {
}
