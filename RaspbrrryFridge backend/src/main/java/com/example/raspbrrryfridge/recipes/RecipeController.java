package com.example.raspbrrryfridge.recipes;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipes")
@CrossOrigin
public class RecipeController {

    RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public void addRecipe(){

    }

    public void deleteRecipe(){

    }

    public void editRecipe(){

    }

    public Optional<Recipe> findRecipeById(int id){
        return recipeService.findRecipeById(id);
    }

}
