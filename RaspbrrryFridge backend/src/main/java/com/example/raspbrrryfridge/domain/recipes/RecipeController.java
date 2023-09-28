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
    public ResponseEntity<String> deleteRecipe(@PathVariable int id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok("Successfully deleted the Recipe");
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<String> editRecipe(@PathVariable int id, @RequestBody RecipeDto recipeDto){
        if(!recipeValidator.isValid(recipeDto)){
            return ResponseEntity.badRequest().body("The recipe you sent was missing information");
        }
        recipeService.editRecipe(id, recipeDto);
        return ResponseEntity.ok("Successfully edited the Recipe");
    }

    @GetMapping("/findById/{id}")
    public Optional<Recipe> findRecipeById(@PathVariable int id){
        return recipeService.findRecipeById(id);
    }

}
