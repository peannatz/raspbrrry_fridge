package com.example.raspbrrryfridge.recipes;

import com.example.raspbrrryfridge.products.Product;
import com.example.raspbrrryfridge.products.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    RecipeRepository recipeRepository;
    ProductService productService;

    public RecipeService(RecipeRepository recipeRepository, ProductService productService) {
        this.recipeRepository = recipeRepository;
        this.productService = productService;
    }

    public void addRecipe(Recipe recipe){
        recipeRepository.save(recipe);
    }

    public void deleteRecipe(int id){
        recipeRepository.deleteById(id);
    }

    public void editRecipe(Recipe recipe){
        Recipe oldRecipe = recipeRepository.findById(recipe.getId()).orElseThrow(()->new RuntimeException("Recipe not found"));
    }
    public Optional<Recipe> findRecipeById(int id){
        return recipeRepository.findById(id);
    }
}
