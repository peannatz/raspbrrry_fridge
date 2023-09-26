package com.example.raspbrrryfridge.products;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }

    public Optional<Product> findProductById(int id){
        return productRepository.findById(id);
    }

    public void editProduct(int id, Product product){
        Product oldProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        oldProduct.setName(product.getName());
        oldProduct.setWeight(product.getWeight());
        productRepository.save(oldProduct);
    }

}
