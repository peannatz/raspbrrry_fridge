package com.example.raspbrrryfridge.domain.recipes;

import com.example.raspbrrryfridge.system.conversion.ValidatorService;
import org.springframework.stereotype.Service;

@Service
public class RecipeValidator implements ValidatorService<RecipeDto> {
    @Override
    public Boolean isValid(RecipeDto recipeDto) {
        if(recipeDto.name() == null || recipeDto.name().isEmpty()){
            return false;
        }
        if(recipeDto.description() == null || recipeDto.description().isEmpty()){
            return false;
        }
        if(recipeDto.portions() <= 0){
            return false;
        }
        if(recipeDto.products().isEmpty()){
            return false;
        }
        return true;
    }
}
