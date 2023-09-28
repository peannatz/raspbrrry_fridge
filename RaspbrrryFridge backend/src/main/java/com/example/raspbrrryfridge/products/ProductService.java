package com.example.raspbrrryfridge.products;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.name());
        product.setWeight(productDto.weight());
        product.setMhd(LocalDate.parse(productDto.mhd()));
        product.setUrl(productDto.url());
        product.setEan(productDto.ean());
        productRepository.save(product);
    }

    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }

    public void editProduct(int id, Product product){
        Product oldProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        oldProduct.setId(id);
        oldProduct.setName(product.getName());
        oldProduct.setWeight(product.getWeight());
        oldProduct.setMhd(product.getMhd());
        oldProduct.setUrl(product.getUrl());
        oldProduct.setEan(product.getEan());
        productRepository.save(oldProduct);
    }

    public Optional<Product> findProductById(int id){
        return productRepository.findById(id);
    }

    public List<Product> findAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    public Optional<Product> findProductByMhd(LocalDate mhd){
        return productRepository.findByMhd(mhd);
    }
}
