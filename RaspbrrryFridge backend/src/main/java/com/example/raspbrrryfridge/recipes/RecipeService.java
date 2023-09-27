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

    public void addRecipe(RecipeDto recipeDto){
        Recipe recipe = new Recipe();
        recipe.setName(recipeDto.name());
        recipe.setDescription(recipeDto.description());
        recipe.setPortions(recipeDto.portions());
        recipe.setProducts(recipeDto.products());
        recipeRepository.save(recipe);
    }

    public void deleteRecipe(int id){
        recipeRepository.deleteById(id);
    }

    public void editRecipe(int id, RecipeDto recipeDto){
        Recipe oldRecipe = recipeRepository.findById(id).orElseThrow(()->new RuntimeException("Recipe not found"));
        oldRecipe.setId(id);
        oldRecipe.setName(recipeDto.name());
        oldRecipe.setDescription(recipeDto.description());
        oldRecipe.setPortions(recipeDto.portions());
        oldRecipe.setProducts(recipeDto.products());
        recipeRepository.save(oldRecipe);
    }
    public Optional<Recipe> findRecipeById(int id){
        return recipeRepository.findById(id);
    }
}
