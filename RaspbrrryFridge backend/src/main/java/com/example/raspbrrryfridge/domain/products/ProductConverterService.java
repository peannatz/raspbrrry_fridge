package com.example.raspbrrryfridge.domain.products;

import com.example.raspbrrryfridge.system.conversion.ConverterService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProductConverterService implements ConverterService<ProductDto, Product> {
    @Override
    public Product convertToEntity(ProductDto productDto, Product product) {
        product.setName(productDto.getName());
        product.setWeight(productDto.getWeight());
        product.setMhd(LocalDate.parse(productDto.getMhd()));
        product.setUrl(productDto.getUrl());
        product.setEan(productDto.getEan());
        product.setTag(productDto.getTag());
        return product;
    }
}
