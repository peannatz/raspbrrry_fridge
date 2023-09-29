package com.example.raspbrrryfridge.domain.products;

import com.example.raspbrrryfridge.system.conversion.ConverterService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ProductConverterService implements ConverterService<ProductDto, Product> {
    @Override
    public Product convertToEntity(ProductDto productDto, Product product) {
        product.setName(productDto.name());
        product.setWeight(productDto.weight());
        product.setMhd(LocalDate.parse(productDto.mhd()));
        product.setUrl(productDto.url());
        product.setEan(productDto.ean());
        product.setTag(productDto.tag());
        return product;
    }
}
