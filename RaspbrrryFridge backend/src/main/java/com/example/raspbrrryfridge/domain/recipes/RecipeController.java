package com.example.raspbrrryfridge.domain.recipes;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/recipes")
@CrossOrigin
public class RecipeController {

    RecipeService recipeService;
    RecipeValidator recipeValidator;

    public RecipeController(RecipeService recipeService, RecipeValidator recipeValidator) {
        this.recipeService = recipeService;
        this.recipeValidator = recipeValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRecipe(@RequestBody RecipeDto recipeDto){
        if(!recipeValidator.isValid(recipeDto)){
            return ResponseEntity.badRequest().body("This recipe is missing Information");
        }
        recipeService.addRecipe(recipeDto);
        return ResponseEntity.ok("Successfully added Recipe");

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
