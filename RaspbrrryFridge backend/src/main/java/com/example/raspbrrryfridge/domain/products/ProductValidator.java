package com.example.raspbrrryfridge.domain.products;

import com.example.raspbrrryfridge.system.conversion.ValidatorService;
import org.springframework.stereotype.Service;

@Service
public class ProductValidator implements ValidatorService<ProductDto> {
    @Override
    public Boolean isValid(ProductDto productDto) {
        if(productDto == null){
            return false;
        }
        if(productDto.name() == null || productDto.name().isEmpty() || !productDto.name().matches("^[a-zA-Z]*$")){
            return false;
        }
        if(productDto.weight() <= 0){
            return false;
        }
        if(productDto.url() == null || productDto.url().isEmpty()){
            return false;
        }
        if(productDto.ean() == null){
            return false;
        }
        return true;

    }
}
