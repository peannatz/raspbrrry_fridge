package com.example.raspbrrryfridge.domain.recipes;

import com.example.raspbrrryfridge.domain.products.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeService {

    RecipeRepository recipeRepository;
    ProductService productService;
    RecipeConverterService recipeConverterService;

    public RecipeService(RecipeRepository recipeRepository, ProductService productService, RecipeConverterService recipeConverterService) {
        this.recipeRepository = recipeRepository;
        this.productService = productService;
        this.recipeConverterService = recipeConverterService;
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
        oldRecipe = recipeConverterService.convertToEntity(recipeDto, oldRecipe);
        recipeRepository.save(oldRecipe);
    }
    public Optional<Recipe> findRecipeById(int id){
        return recipeRepository.findById(id);
    }
}
