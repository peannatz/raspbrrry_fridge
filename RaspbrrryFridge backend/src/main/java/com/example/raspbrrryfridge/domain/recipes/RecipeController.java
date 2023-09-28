package com.example.raspbrrryfridge.domain.recipes;


import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/recipes")
@CrossOrigin
public class RecipeController {

    RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @PostMapping("/add")
    public void addRecipe(@RequestBody RecipeDto recipeDto){
        recipeService.addRecipe(recipeDto);

    }

    @PostMapping("/delete/{id}")
    public void deleteRecipe(@PathVariable int id){
        recipeService.deleteRecipe(id);
    }

    @PostMapping("/edit/{id}")
    public void editRecipe(@PathVariable int id, @RequestBody RecipeDto recipeDto){
        recipeService.editRecipe(id, recipeDto);
    }

    @GetMapping("/findById/{id}")
    public Optional<Recipe> findRecipeById(@PathVariable int id){
        return recipeService.findRecipeById(id);
    }

}
