package com.example.raspbrrryfridge.recipes;

import com.example.raspbrrryfridge.conversion.ConverterService;
import org.springframework.stereotype.Service;

@Service
public class RecipeConverterService implements ConverterService<RecipeDto, Recipe> {

    @Override
    public Recipe convertToEntity(RecipeDto recipeDto, Recipe recipe) {
        recipe.setName(recipeDto.name());
        recipe.setDescription(recipeDto.description());
        recipe.setPortions(recipeDto.portions());
        recipe.setProducts(recipeDto.products());
        return recipe;
    }
}
